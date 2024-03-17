package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonService {
    private final PeopleRepository peopleRepository;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return person;
    }


    public List<Person> getAllPeople() {
        // Используем метод findAll() для получения списка всех записей
        return peopleRepository.findAll();
    }

    public  Optional<Person> findByUsername(String username){
        return  peopleRepository.findByUsername(username);
    }


    public List<Person> findPersonsByDragon(Dragon dragon) {
        return peopleRepository.findByDragons(dragon);
    }

    public void addDragonToPerson(int personId, Dragon dragon) {
        Person person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getDragons().add(dragon);
        peopleRepository.save(person);
    }
    public void deleteDragonToPerson(int personId, Dragon dragon) {
        Person person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.getDragons().remove(dragon);
        peopleRepository.save(person);
    }


    public List<Dragon> getDragonsForPerson(int personId) {
        Person person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return person.getDragons();
    }

    public  Optional<List<Person>> findByAbandonTrue(){
        return peopleRepository.findByAbandonTrue();
    }


}
