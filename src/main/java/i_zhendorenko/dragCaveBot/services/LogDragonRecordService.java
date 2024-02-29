package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.LogDragonRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.LogDragonRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogDragonRecordService {

    private final LogDragonRecordRepository logDragonRecordRepository;

    @Autowired
    public LogDragonRecordService(LogDragonRecordRepository logDragonRecordRepository) {
        this.logDragonRecordRepository = logDragonRecordRepository;
    }

    public LogDragonRecord saveLogRecord(LogDragonRecord logDragonRecord) {
        return logDragonRecordRepository.save(logDragonRecord);
    }

    public List<LogDragonRecord> getAllLogRecords() {
        return logDragonRecordRepository.findAll();
    }

    public List<LogDragonRecord> getLogRecordsForPerson(Person person) {
        return logDragonRecordRepository.findByPerson(person);
    }

    public List<LogDragonRecord> getLogRecordsForDragon(Dragon dragon) {
        return logDragonRecordRepository.findByDragon(dragon);
    }

    public void deleteLogRecord(LogDragonRecord logDragonRecord) {
        logDragonRecordRepository.delete(logDragonRecord);
    }

}
