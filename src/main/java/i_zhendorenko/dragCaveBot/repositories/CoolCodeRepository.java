package i_zhendorenko.dragCaveBot.repositories;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolCodeRepository extends JpaRepository<CoolCode, Integer> {
    List<CoolCode> findByPerson(Person person);
    // Дополнительные методы могут быть добавлены здесь, если необходимо
    Page<CoolCode> findByPerson(Person person, Pageable pageable);
}
