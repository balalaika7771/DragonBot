package i_zhendorenko.dragCaveBot.repositories;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.LogCodeRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface LogCodeRecordRepository extends JpaRepository<LogCodeRecord, Long> {

    List<LogCodeRecord> findByPerson(Person person);
}
