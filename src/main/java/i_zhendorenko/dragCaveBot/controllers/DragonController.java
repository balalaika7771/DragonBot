package i_zhendorenko.dragCaveBot.controllers;


import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import i_zhendorenko.dragCaveBot.services.DragonService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DragonController {

    private final DragonService dragonService;
    private final PersonService personService;

    @Autowired
    public DragonController(DragonService dragonService, PersonService personService) {
        this.dragonService = dragonService;
        this.personService = personService;
    }

    @GetMapping("/dragons")
    public String showAllDragons(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        // Получаем список всех драконов
        List<Dragon> allDragons = dragonService.getAllDragons();

        // Получаем список драконов у текущего пользователя
        List<Dragon> dragonsForPerson = personService.getDragonsForPerson(person.getId());

        model.addAttribute("dragonsForPerson", dragonsForPerson);

        model.addAttribute("allDragons", allDragons);


        return "dragon/dragons";
    }

    @PostMapping("/dragons/add/{dragonId}")
    public String addDragonToPerson(@PathVariable int dragonId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        // Находим дракона по его идентификатору
        Optional<Dragon> dragon = dragonService.getDragonById(dragonId);
        if(dragon.isEmpty()){
            return "redirect:/dragons";
        }
        // Добавляем дракона текущему пользователю
        personService.addDragonToPerson(person.getId(), dragon.get());

        return "redirect:/dragons";
    }
    @PostMapping("/dragons/remove/{dragonId}")
    public String deleteDragonToPerson(@PathVariable int dragonId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        Optional<Dragon> dragon = dragonService.getDragonById(dragonId);
        if(dragon.isEmpty()){
            return "redirect:/dragons";
        }
        personService.deleteDragonToPerson(person.getId(), dragon.get());

        return "redirect:/dragons";
    }
}
