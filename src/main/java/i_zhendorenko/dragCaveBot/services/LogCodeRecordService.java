package i_zhendorenko.dragCaveBot.services;


import i_zhendorenko.dragCaveBot.models.LogCodeRecord;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.LogCodeRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogCodeRecordService {

    private final LogCodeRecordRepository logCodeRecordRepository;

    public LogCodeRecordService(LogCodeRecordRepository logCodeRecordRepository) {
        this.logCodeRecordRepository = logCodeRecordRepository;
    }

    public LogCodeRecord saveLogRecord(LogCodeRecord logCodeRecord) {
        return logCodeRecordRepository.save(logCodeRecord);
    }
    public List<LogCodeRecord> getLogRecordsForPerson(Person person) {
        return logCodeRecordRepository.findByPerson(person);
    }

}
