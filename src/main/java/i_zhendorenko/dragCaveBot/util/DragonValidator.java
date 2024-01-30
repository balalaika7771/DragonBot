package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.DragonService;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class DragonValidator implements Validator {
    private final DragonService dragonService;

    public DragonValidator(DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Dragon.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Dragon _dragon = (Dragon) o;
        try {
            Optional<Dragon> dragon = dragonService.findByName(_dragon.getName());
            if (dragon.isPresent()) {
                errors.rejectValue("name", "", "Имя занято");
            }
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
