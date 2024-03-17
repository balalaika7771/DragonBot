package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.DTO.DragonDTO;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import i_zhendorenko.dragCaveBot.services.DragonService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dragons")
public class DragonController {

    private final DragonService dragonService;
    private final PersonService personService;

    @Autowired
    public DragonController(DragonService dragonService, PersonService personService) {
        this.dragonService = dragonService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<DragonDTO>> showAllDragons() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        List<DragonDTO> allDragons = dragonService.getAllDragons()
                .stream().map(DragonDTO::new).collect(Collectors.toList());

        return new ResponseEntity<>(allDragons, HttpStatus.OK);
    }
    @GetMapping("/myDragon")
    public ResponseEntity<List<DragonDTO>> myDragon() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();


        List<DragonDTO> dragonsForPerson = personService.getDragonsForPerson(person.getId())
                .stream().map(DragonDTO::new).collect(Collectors.toList());


        return new ResponseEntity<>(dragonsForPerson, HttpStatus.OK);
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<Void> addDragonToPerson(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        try {
            Dragon dragon = dragonService.findByName(name).orElseThrow(() -> new NotFoundException("Dragon not found"));
            personService.addDragonToPerson(person.getId(), dragon);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/remove/{name}")
    public ResponseEntity<Void> deleteDragonFromPerson(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Person person = ((PersonDetails) principal).getPerson();

        try {
            Dragon dragon = dragonService.findByName(name).orElseThrow(() -> new NotFoundException("Dragon not found"));
            personService.deleteDragonToPerson(person.getId(), dragon);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
