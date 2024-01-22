package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import i_zhendorenko.dragCaveBot.models.Person;

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
}
