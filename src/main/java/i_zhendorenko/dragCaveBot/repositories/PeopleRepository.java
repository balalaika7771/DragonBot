package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.Dragon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import i_zhendorenko.dragCaveBot.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);

    List<Person> findByDragons(Dragon dragon);
    List<Person> findAll();
    List<Dragon> findDragonsById(int personId);
    Optional<List<Person>> findByAbandonTrue();
}
