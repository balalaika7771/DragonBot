package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import i_zhendorenko.dragCaveBot.models.Person;

import java.util.Optional;

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


}
