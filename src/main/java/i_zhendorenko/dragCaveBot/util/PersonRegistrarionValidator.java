package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.CookieAuthService;
import i_zhendorenko.dragCaveBot.services.DragonAuthService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;


@Component
public class PersonRegistrarionValidator implements Validator {
    private final CookieAuthService cookieAuthService;

    private final PersonService personService;
    private final DragonAuthService dragonAuthService;
    @Autowired
    public PersonRegistrarionValidator(CookieAuthService cookieAuthService, PersonService personService, DragonAuthService dragonAuthService) {
        this.cookieAuthService = cookieAuthService;
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
        if(!person.isEmpty()){
            errors.rejectValue("password", "", "пользователь уже есть");
        }
        List<String> cookies = dragonAuthService.auth(person.get().getUsername(),person.get().getPassword());
        if (cookies == null) {
            errors.rejectValue("password", "", "Пароль не действителен!");
        }

    }
}

