package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.DTO.CodeDTO;
import i_zhendorenko.dragCaveBot.models.Code;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    // Получение всех кодов по объекту Person
    public List<Code> getAllCodesByPerson(Person person) {
        return codeRepository.findByPerson(person);
    }

    // Получение кода по его идентификатору
    public Optional<Code> getCodeById(int codeId) {
        return codeRepository.findById(codeId);
    }

    // Добавление нового кода
    public Code addCode(Code newCode) {
        return codeRepository.save(newCode);
    }

    // Обновление кода
    public Code updateCode(Code updatedCode) {
        return codeRepository.save(updatedCode);
    }

    // Удаление кода по его идентификатору
    public void deleteCodeById(int codeId) {
        codeRepository.deleteById(codeId);
    }

    // Удаление кода по самому объекту Code
    public void deleteCode(Code code) {
        codeRepository.delete(code);
    }

    public Page<Code> getAllCodesByPersonPaged(Person person, PageRequest pageable) {
        return  codeRepository.findByPerson(person,pageable);
    }
}
