package i_zhendorenko.dragCaveBot.repositories;
import i_zhendorenko.dragCaveBot.models.Code;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    List<Code> findByPerson(Person person);
    // Дополнительные методы могут быть добавлены здесь, если необходимо
    Page<Code> findByPerson(Person person, Pageable pageable);
}
