package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import i_zhendorenko.dragCaveBot.models.Person;

/**
 * @author Neil Alishev
 */
@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void register(@org.jetbrains.annotations.NotNull Person person) {
        person.setRole("ROLE_USER");
        peopleRepository.save(person);
    }
}
