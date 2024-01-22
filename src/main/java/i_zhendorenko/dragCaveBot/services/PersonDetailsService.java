package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import i_zhendorenko.dragCaveBot.models.Person;

import java.util.Optional;


@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }



    public void updatePassword(UserDetails personDetails, String password) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(personDetails.getUsername());

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        person.get().setPassword(password);
        peopleRepository.save(person.get());
        return;
    }
}
