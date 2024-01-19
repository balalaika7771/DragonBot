package i_zhendorenko.dragCaveBot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import i_zhendorenko.dragCaveBot.models.Person;

import java.util.Optional;

/**
 * @author Neil Alishev
 */
@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

}
