package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.util.PersonRegistrarionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.RegistrationService;
import i_zhendorenko.dragCaveBot.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonRegistrarionValidator personRegistrarionValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonRegistrarionValidator personRegistrarionValidator) {
        this.registrationService = registrationService;
        this.personRegistrarionValidator = personRegistrarionValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        personRegistrarionValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/login";

        registrationService.register(person);

        return "redirect:/auth/login";
    }

}
