package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import i_zhendorenko.dragCaveBot.models.Person;

import java.util.List;


@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final DragonAuthService dragonAuthService;

    private final CookieAuthService cookieAuthService;
    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, DragonAuthService dragonAuthService, CookieAuthService cookieAuthService) {
        this.peopleRepository = peopleRepository;
        this.dragonAuthService = dragonAuthService;
        this.cookieAuthService = cookieAuthService;
    }

    @Transactional
    public void register(@org.jetbrains.annotations.NotNull Person person) {
        person.setRole("ROLE_USER");
        peopleRepository.save(person);

        List<String> cookies = dragonAuthService.auth(person.getUsername(),person.getPassword());
        if (cookies != null) {
            cookieAuthService.saveCookieAuth(person,cookies);
        }
    }
}
