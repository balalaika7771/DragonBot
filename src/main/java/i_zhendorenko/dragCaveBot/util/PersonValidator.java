package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.DragonAuthService;

import java.util.List;

/**
 * @author Neil Alishev
 */
@Component
    public class PersonValidator implements Validator {

    private final DragonAuthService dragonAuthService;
    @Autowired
    public PersonValidator( DragonAuthService dragonAuthService) {
        this.dragonAuthService = dragonAuthService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        List<String> cookies = dragonAuthService.auth(person.getUsername(),person.getPassword());
        if (cookies != null) {
            System.out.println("Куки после аутентификации: " + cookies);
        } else {
            errors.rejectValue("password", "", "Пароль не действителен!");
        }

    }
}
