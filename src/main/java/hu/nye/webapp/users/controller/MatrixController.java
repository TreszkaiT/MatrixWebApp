package hu.nye.webapp.users.controller;

import hu.nye.webapp.users.dto.MatrixDTO;
import hu.nye.webapp.users.service.MatrixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ebben vannak az EndPoint-ok
 *
 * Entity-k és adatbázis művelet itt nem lehet!!! meg egyetlen más controller-ben sem!!
 *
 * ezek a metódusok fogják lekezelni a kéréseket az egyes endpointokra pl. GET/movies  a https://editor.swagger.io/ -oldalon GET/POST/....
 *
 * a UserDTO bevezetésével ez az osztály már a UserService-en fog függni; az implementációt meg majd a Spring magától intézi, nekünk nem kell
 *
 */

@RestController
@RequestMapping(path = "/matrixes")
public class MatrixController {

    private final MatrixService matrixService;                      // e felé nem kell @Autowire annotáció, mert Spring x. verzió felett már nem szükséges, ha az osztály felett ott az annotáció kitéve (RestContorller)

    public MatrixController(MatrixService matrixService) {            // ide, vagy egy sorral fentebb nem kell az @Autowired Annotáció, mert Spring x. verzió felett már megnézi az osztály Annotációját @RestController, és tudja, hogy annak a feladata behzúni ezeket, és ez a konstruktor egyértelmű, és egyszerű
        this.matrixService = matrixService;
    }

    // usereket olvas DB-ból: ez pedig már a UserDTO-t fog visszaadni, és a userService-t fog használni
    @RequestMapping(method = RequestMethod.GET)      // RequestMapping: megmondjuk, hogy ez a metódus a GET/users  hívásra alkalmas;;;; azaz ez egy kérés Mappalése, ha bejön egy kérés, akkor azt le tudjuk mappelni erre a metódusra  CTRL+P metódusainak kilistázása
    public ResponseEntity<List<MatrixDTO>> findAll(){
        return ResponseEntity.ok(matrixService.findAll());
    }

    // adatokat ír az adatbázisba:  (@RequestBody UserDTO userDTO): ha bejön egy kérés, és volt RequestBody-ja, akkor átkonvertálja azt UserDTO objektummá, és ezzel tudnunk dolgozni ezen metódus törzsében
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MatrixDTO> create(@RequestBody MatrixDTO matrixDTO){
        MatrixDTO savedMatrix =  matrixService.create(matrixDTO);                           // itt meg meghívom az Implementáció create metódusát
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMatrix);
    }

    // id alapján kérjük le a filmeket
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)                              // /{id}  kapott id
    //public ResponseEntity<MatrixDTO> findById(@PathVariable(name = "id") Long identifier){       // @PathVariable Long id ezzel a kapott /{id}-t tudjuk kezelni ezen metóduson belül
    public String findById(@PathVariable(name = "id") Long identifier){
        Optional<MatrixDTO> optionalMatrixDTO = matrixService.findById(identifier);

        ResponseEntity<MatrixDTO> response;
        if (optionalMatrixDTO.isPresent()) {                                                     // ha van benne bármilyen érték, azaz megtalálltuk a filmet
            response = ResponseEntity.ok(optionalMatrixDTO.get());                               // akkor kiszedem az értékét
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);                  // amúgy meg nem találtam meg
        }

        return MatrixSzamolas(response);

        //return response;
        //return ResponseEntity.ok(optionalMatrixDTO.get());
    }

    private String MatrixSzamolas(ResponseEntity<MatrixDTO> response) {
        Integer row = response.getBody().getIndexI();
        Integer col = response.getBody().getIndexJ();
        String elements = response.getBody().getElemnts();

        Integer[][] matrix = new Integer[row][col];

        matrix = MatrixLetrehozas(row, col, elements);

        return NyeregpontMeghatarozas(row, col, matrix);

    }

    private Integer[][] MatrixLetrehozas(Integer row, Integer col, String elements){
        String[] parts = elements.split(",");                                                       // String feldarabolása , mentén
        Integer[][] matrix = new Integer[row][col];

        int k=0;
        for(int i=0; i<matrix.length; i++)
            for(int j=0; j<matrix[i].length; j++) {
                matrix[i][j] = Integer.parseInt(parts[k]);                                              // Integer[][] tömb készítése belőle
                k++;
            }

        System.out.println("\nA Matrix: \n");
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                System.out.printf("%5d ", matrix[i][j]);
            }
            System.out.println();
        }
        return matrix;
    }

    private String NyeregpontMeghatarozas(Integer row, Integer col, Integer[][] matrix) {
        String result = "";
        int rowMin=0, colMax=0, tar, minMaxIndexI=0, minMaxIndexJ=0, maxMinIndexI=0, maxMinIndexJ=0;
        int[] minek = new int[row]; int[] minekI = new int[row]; int[] minekJ = new int[row];
        int[] maxok = new int[col]; int[] maxokI = new int[col]; int[] maxokJ = new int[col];

            // sorMinMax kinyerése
        System.out.println("\nSoronkent vegiglepegetve: \n");
        for(int i=0; i<matrix.length; i++) {                                                // sorokon végigmegyek
            tar = matrix[i][0];                                                             // minden sor első elemét veszem
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < tar) tar = matrix[i][j];
                if(j==matrix[i].length-1) {
                    minek[i] = tar;
                    minekI[i] = i;
                    minekJ[i] = j;
                    if (i == 0) {                                                               // amíg nincs a rowMin-nek értéke, addig adok neki
                        rowMin = tar;
                        minMaxIndexI = i;
                        minMaxIndexJ = j;
                    } else {                                                                           // ha már van, akkor összehasonlítom az előzővel
                        //System.out.print(" "+rowMin);
                        if (tar > rowMin) {
                            rowMin = tar;
                            minMaxIndexI = i;
                            minMaxIndexJ = j;
                        }
                    }
                }
                System.out.printf("%5d ", matrix[i][j]);
            }
            System.out.println(" ");
        }

        System.out.println("\nOszloponkent vegiglepegetve: \n");
            // oszlopMayMin kinyerése
        for(int j=0; j<matrix[col-1].length; j++){
            tar = matrix[0][j];
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] > tar) tar = matrix[i][j];
                if(i==matrix.length-1){
                    maxok[j] = tar;
                    maxokI[j] = i;
                    maxokJ[j] = j;
                    if (j == 0) {                                                               // amíg nincs a rowMin-nek értéke, addig adok neki
                        colMax = tar;
                        maxMinIndexI = i;
                        maxMinIndexJ = j;
                    }else {                                                                         // ha már van, akkor összehasonlítom az előzővel
                        //System.out.print(" "+colMax);
                        if (tar < colMax) {
                            colMax = tar;
                            maxMinIndexI = i;
                            maxMinIndexJ = j;
                        }
                    }
                }
                System.out.printf("%5d ", matrix[i][j]);
            }
            System.out.println(" ");
        }

        System.out.println("\nA Minek: \n");
        for(int i=0; i<minek.length; i++)
            System.out.print(minek[i]+" ");

        System.out.println("\nA Maxok: \n");
        for(int j=0; j<maxok.length; j++)
            System.out.print(maxok[j]+" ");

        System.out.println("\nA MinMax es MaxMin: \n");
        System.out.println(rowMin+" "+colMax);


        boolean vanNyeregpont = false;
        List<Integer> nyeregPont = new ArrayList<Integer>();
        List<Integer> nyeregPontI = new ArrayList<Integer>();
        List<Integer> nyeregPontJ = new ArrayList<Integer>();
        //int[] nyeregPont = new int[col];
        //int[] nyeregPontI = new int[col];
        //int[] nyeregPontJ = new int[col];
        int ny = 0;
        //ha rowMin=colMax ==>> akkor vizsgáljuk meg, hogy egy helyen vannak-e, amúgy nincs nyeregpont
        if(rowMin==colMax) {                                        // ekkor lehet nyeregpontja, ha pont jó helyen vannak az értékek

            // ha a minek értékének (0) a táblázatban megkeressük, és a sorszáma=min sorszámával és oszlopszáma meg a max oszlopszámával
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if(matrix[i][j]==rowMin){                       // ha az adott mátrix-beli elem pont a keresett elem
                        for(int k=0; k<minek.length; k++)
                            if(matrix[i][j]==minek[k] && minekI[k]==i)      //ha benne van a min-listában, és a sorindexe=a mátrix sorindexével
                                for(int t=0; t<maxok.length; t++)
                                    if(matrix[i][j]==maxok[t] && maxokJ[t]==j) {nyeregPont.add(matrix[i][j]); nyeregPontI.add(i); nyeregPontJ.add(j); ny++;}; //{nyeregPont[ny]=matrix[i][j]; nyeregPontI[ny]=i; nyeregPontJ[ny]=j; ny++;}
                    }
                }
            }

            if(ny>0) {
                vanNyeregpont=true;

                result +="\nA Matrix és min-ek, max-ok: \n";
                for(int i = 0; i < row; i++)
                {
                    for(int j = 0; j < col; j++)
                    {
                        result +="\t "+matrix[i][j];
                    }
                    result +="\t "+minek[i];
                    result +="\n";
                }
                for(int i=0; i<maxok.length; i++) result +="\t "+maxok[i];

                System.out.println("\nA nyeregPontok, (i,j): \n");
                result +="\nA nyeregPontok, (i,j): \n";
                for(int l=nyeregPont.size()-1;l>=0;l--) {
                    System.out.println(nyeregPont.get(l) + " (" + (nyeregPontI.get(l) + 1) + "," + (nyeregPontJ.get(l) + 1) + ")");
                    result += nyeregPont.get(l) + " (" + (nyeregPontI.get(l) + 1) + "," + (nyeregPontJ.get(l) + 1) + ") \n";
                }
            }else{
                result = "Nincs nyeregpont, mert bár a MinMax és a MaxMin megegyezik, de nem jó koordinátákban!";
            }
        }else{
            System.out.println("Nincs nyeregpont, mert a MinMax és a MaxMin nem egyezik meg sehol!");
            result = "Nincs nyeregpont, mert a MinMax és a MaxMin nem egyezik meg sehol!";
        }
    return result;
    }

}
