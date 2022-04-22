package hu.nye.webapp.users.service;

import hu.nye.webapp.users.dto.MatrixDTO;

import java.util.List;
import java.util.Optional;

/**
 * service réteg: itt van az üzeleti logika
 * szerepe: az entity és repository rétegeket tudja hívni; azaz az adatbázis rétegeit, dolgait fel tudja használni
 *
 */
public interface MatrixService {

    // Ez a metódus egy UserDTO típusú Listát ad vissza
    List<MatrixDTO> findAll();

    // user mentése. Mentés után visszakapjuk a UserDTO-t
    MatrixDTO create(MatrixDTO matrixDTO);

    // film id alapján való keresése
    Optional<MatrixDTO> findById(Long id);
}
