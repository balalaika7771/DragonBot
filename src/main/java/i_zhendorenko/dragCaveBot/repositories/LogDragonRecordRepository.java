package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.LogDragonRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogDragonRecordRepository extends JpaRepository<LogDragonRecord, Long> {

    List<LogDragonRecord> findByPerson(Person person);

    List<LogDragonRecord> findByDragon(Dragon dragon);

    Optional<LogDragonRecord> findByIdAndPerson(Long id, Person person);

    Optional<LogDragonRecord> findByIdAndDragon(Long id, Dragon dragon);

}
