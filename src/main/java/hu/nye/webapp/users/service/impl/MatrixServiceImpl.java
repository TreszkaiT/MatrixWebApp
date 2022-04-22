package hu.nye.webapp.users.service.impl;

import hu.nye.webapp.users.dto.MatrixDTO;
import hu.nye.webapp.users.entity.Matrix;
import hu.nye.webapp.users.repository.MatrixRepository;
import hu.nye.webapp.users.service.MatrixService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

/**
 *
 * Ő a konkrét implementációja a MovieService.java interface-nak
 * Ő tudja hivogatni a UserRepository-t, és felszedi az adatbázisból a kellő dolgokat, majd átalakítja findAll() metódus Steam-jével megfelelő formátumba, és visszaadja a Controller felé
 *
 */

// és azrét, hogy a Spring be tudja neki adni a MovieRepository függőséget, és a függőséget át tudja majd adni a Controllerünknek, ahhoz ebből az osztályból egy Bean-t kell csinálni egy Annotációval egyszerűen
@Service
public class MatrixServiceImpl implements MatrixService {

    private final MatrixRepository matrixRepository;    // az implementáció a UserRepository-val gyűjtse össze az adatokat az adatbázisból
    private final ModelMapper modelMapper;          // külső osztály így Configuration-on keresztül lesz ebből Bean

    public MatrixServiceImpl(MatrixRepository matrixRepository, ModelMapper modelMapper) {
        this.matrixRepository = matrixRepository;
        this.modelMapper = modelMapper;

        // ha futtatni akarjuk, akkor hogy az adatbázisnak legyen valami tartalma, így ezt ide kell másolni:
        Matrix matrix1 = new Matrix();            // hogy legyen példaadat is
        matrix1.setIndexI(4);
        matrix1.setIndexJ(4);
        matrix1.setElemnts("0,-2,-4,4,-1,-1,-3,3,-2,0,-2,2,4,2,0,0");

        matrixRepository.save(matrix1);         // lementi az adatbázisba


    }

    // --- UserServiceImpl  implementáció kiegészítése
    // userek kilistázása
    @Override
    public List<MatrixDTO> findAll() {
        List<Matrix> matrixList = matrixRepository.findAll();                 // az összes usert felszedjük a userRepositoryval, ami az adatbázisban van, és elmentük ezt egy Listába

        return matrixList.stream()                                        // és a User-ekkt beleteszem a stream-be, és az ősszes elemre végrehajtok egy map-pelést; azaz átlakítom mássá az elemeket
                .map(matrix -> modelMapper.map(matrix, MatrixDTO.class))      // User -> UserDTO lesz
                .collect(Collectors.toList());                          // és ezek után ezzel a művelettel begyűjtöm egy listába. Majd gyakorlatilag ezeket vissza is tudom már így adni a return-t eléírva
    }

    // user mentése
    @Override
    public MatrixDTO create(MatrixDTO matrixDTO) {
        Matrix matrixToSave = modelMapper.map(matrixDTO, Matrix.class);         // UserDTO -> User lesz azaz Entity-vé
        matrixToSave.setId(null);
        Matrix savedMatrix = matrixRepository.save(matrixToSave);               // ez menti el az adatbázisba, és ez vissza is adja azt az Enity-t amit elmentett; ez kell, mert a JPA mentés során automatikusan generál neki egy ID-t
        return modelMapper.map(savedMatrix, MatrixDTO.class);               // és a kapott objektumot visszaalakítom UserDTO-vá
    }
    @Override
    //public Optional<MatrixDTO> findById(Long id) {
    public Optional<MatrixDTO> findById(Long id) {
        Optional<Matrix> optionalMatrix = matrixRepository.findById(id);           // Optional<Movie> :  a Move Entity-t becsomagolja egy Optional generikus metódusba. A nullkezeléssel kapcsolatos dolgokat meg tudunk oldani (if(null)-al így már nem kell fogalalkozni)... keresékeknél használható, hoy megtaláltunk-e valamit vagy sem
        return optionalMatrix.map(matrix -> modelMapper.map(matrix, MatrixDTO.class));                                    // Movie Entity -> MovieDTO  azaz, optionalMovie.map(movie -> ha itt szerepel egy movie, ami nem null, azaz nem üres, akkor alakítsd át: modelMapper.map(movie, MovieDTO.class)
    }
}
