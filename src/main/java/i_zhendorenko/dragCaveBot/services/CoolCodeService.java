package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.CoolCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoolCodeService {

    private final CoolCodeRepository coolCodeRepository;

    @Autowired
    public CoolCodeService(CoolCodeRepository coolCodeRepository) {
        this.coolCodeRepository = coolCodeRepository;
    }

    // Получение всех кодов по объекту Person

    public List<CoolCode> getAllCodesByPerson(Person person) {
        return coolCodeRepository.findByPerson(person);
    }

    // Получение кода по его идентификатору
    public Optional<CoolCode> getCodeById(int codeId) {
        return coolCodeRepository.findById(codeId);
    }

    // Добавление нового кода
    public CoolCode addCode(CoolCode newCoolCode) {
        return coolCodeRepository.save(newCoolCode);
    }

    // Обновление кода
    public CoolCode updateCode(CoolCode updatedCoolCode) {
        return coolCodeRepository.save(updatedCoolCode);
    }

    // Удаление кода по его идентификатору
    public void deleteCodeById(int codeId) {
        coolCodeRepository.deleteById(codeId);
    }

    // Удаление кода по самому объекту Code
    public void deleteCode(CoolCode coolCode) {
        coolCodeRepository.delete(coolCode);
    }

    public Page<CoolCode> getAllCodesByPersonPaged(Person person, PageRequest pageable) {
        return  coolCodeRepository.findByPerson(person,pageable);
    }
}
