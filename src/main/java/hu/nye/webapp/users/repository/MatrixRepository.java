package hu.nye.webapp.users.repository;

import hu.nye.webapp.users.entity.Matrix;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ez fogja az adatbázis műveleteket elvégezni
 *
 * ez egy JPA-s repository lesz
 *
 */

public interface MatrixRepository extends JpaRepository<Matrix, Long> {

    // itt azért saját metódusokat is tudunk definiálni, ha szeretnénk nem csak beépítetteket... a Spring Data JPA dokumentációjában utána tudunk olvasni

    // List<User> findByEmailAddressAndLastName(String emailAddress, String lastName);          // And miatt a metódus névből a Spring ki tudja generálni akár az SQL Query-t is!!! docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
}
