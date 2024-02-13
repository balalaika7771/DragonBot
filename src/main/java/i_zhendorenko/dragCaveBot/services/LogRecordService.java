package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.LogRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.LogRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogRecordService {

    private final LogRecordRepository logRecordRepository;

    @Autowired
    public LogRecordService(LogRecordRepository logRecordRepository) {
        this.logRecordRepository = logRecordRepository;
    }

    public LogRecord saveLogRecord(LogRecord logRecord) {
        return logRecordRepository.save(logRecord);
    }

    public List<LogRecord> getAllLogRecords() {
        return logRecordRepository.findAll();
    }

    public List<LogRecord> getLogRecordsForPerson(Person person) {
        return logRecordRepository.findByPerson(person);
    }

    public List<LogRecord> getLogRecordsForDragon(Dragon dragon) {
        return logRecordRepository.findByDragon(dragon);
    }

    public void deleteLogRecord(LogRecord logRecord) {
        logRecordRepository.delete(logRecord);
    }

}
