package hu.nye.webapp.users.dto;

import java.util.Date;
import java.util.Set;

/**
 * Data Transfer Object = csak adattovábbításra használatos osztály ez
 * ugyanazt csinálja, mint a User, csak ez nem Entity lesz, így nem lesz szükségünk a User.java-ban az Entity-s és JPA-s Annotációkra
 *
 * ezt az osztályt használjuk fel a UserService-ban
 *
 * Mivel ez nem Entity, így már nincs szükésünk itt az Annotációkra
 */
public class MatrixDTO {

    private Long id;
    private Integer indexI;
    private Integer indexJ;
    private String elemnts;

    public MatrixDTO() {
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
