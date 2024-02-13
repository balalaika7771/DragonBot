package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.LogRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {

    List<LogRecord> findByPerson(Person person);

    List<LogRecord> findByDragon(Dragon dragon);

    Optional<LogRecord> findByIdAndPerson(Long id, Person person);

    Optional<LogRecord> findByIdAndDragon(Long id, Dragon dragon);

    // Другие необходимые методы, например, поиск по дате, счётчику и т. д.
}
