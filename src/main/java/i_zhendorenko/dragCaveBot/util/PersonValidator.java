package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.services.CookieAuthService;
import i_zhendorenko.dragCaveBot.services.PersonDetailsService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.DragonAuthService;

import java.util.List;
import java.util.Optional;


@Component
public class PersonValidator implements Validator {
    private final CookieAuthService cookieAuthService;
    private final CookieAuthValidator cookieAuthValidator;
    private final PersonService personService;
    private final DragonAuthService dragonAuthService;

    @Autowired
    public PersonValidator(CookieAuthService cookieAuthService, CookieAuthValidator cookieAuthValidator, PersonService personService, DragonAuthService dragonAuthService) {
        this.cookieAuthService = cookieAuthService;
        this.cookieAuthValidator = cookieAuthValidator;
        this.personService = personService;
        this.dragonAuthService = dragonAuthService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
                return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person _person = (Person) o;

        Optional<Person> person = personService.findByUsername(_person.getUsername());
        if (person.isEmpty()) {
            errors.rejectValue("password", "", "Нет пользователя в базе бота");
        }

        Optional<CookieAuth> lastCookieAuthByPerson = cookieAuthService.getLastCookieAuthByPerson(person.get());

        if (person.get().getPassword().equals(_person.getPassword()) && lastCookieAuthByPerson.isPresent()) {
            cookieAuthValidator.validate(lastCookieAuthByPerson.get(), errors);
        } else {
            List<String> cookies = dragonAuthService.auth(_person.getUsername(), _person.getPassword());
            if (cookies == null) {
                errors.rejectValue("password", "", "Пароль не действителен!");
            } else {
                cookieAuthService.saveCookieAuth(person.get(), cookies);
            }
        }


    }
}
