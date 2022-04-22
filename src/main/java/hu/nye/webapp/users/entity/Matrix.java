package hu.nye.webapp.users.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * ebből az osztályból -> adatbázis beli sor és tábla készítése automatikusan
 *
 * ezzel hozzuk létre az adatbázist, és a benne lévő táblákat, fejléceket
 * hogy tudjuk használni a Spring Data JPA-t és adatbázisba dolgokat tudjunk menteni, kell készíteni egy Entity-t
 *
 */

@Entity
public class Matrix {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)             // AutoIncrement lesz így az Id
    private Long id;
    private Integer indexI;
    private Integer indexJ;
    private String elemnts;


    // kell még hozzá egy üres konstruktor ;; mindenképp meg kell adni ;; és gettereket és settereket -> Code menü / Generate / Constructor / Select None
    public Matrix() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndexI() {
        return indexI;
    }

    public void setIndexI(Integer indeyI) {
        this.indexI = indeyI;
    }

    public Integer getIndexJ() {
        return indexJ;
    }

    public void setIndexJ(Integer indexJ) {
        this.indexJ = indexJ;
    }

    public String getElemnts() {
        return elemnts;
    }

    public void setElemnts(String elemnts) {
        this.elemnts = elemnts;
    }
}
