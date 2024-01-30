package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.services.DragonService;
import i_zhendorenko.dragCaveBot.services.HttpClientService;
import i_zhendorenko.dragCaveBot.util.ResponseEjector;
import i_zhendorenko.dragCaveBot.util.DragonValidator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ADMIN')")
 class AdminController {

    private final DragonService dragonService;
    private final HttpClientService httpClientService;
    private  final DragonValidator dragonValidator;
    private  final ResponseEjector responseEjector;
    @Autowired
    public AdminController(DragonService dragonService, HttpClientService httpClientService, DragonValidator dragonValidator, ResponseEjector responseEjector) {
        this.dragonService = dragonService;
        this.httpClientService = httpClientService;
        this.dragonValidator = dragonValidator;
        this.responseEjector = responseEjector;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        return "admin/admin";
    }

    @PostMapping("/admin/dragons/add")
    public String addDragon(Dragon dragon, BindingResult bindingResult) {
        dragonValidator.validate(dragon, bindingResult);

        if (!bindingResult.hasErrors())
            dragonService.addDragon(dragon);
        return "redirect:/admin";
    }

    @GetMapping("/admin/dragons/update")
    public String addDescriptionToDragon(String description) throws NotFoundException {

        ResponseEntity<String> response = httpClientService.sendGetRequest("https://dragcave.fandom.com/wiki/Egg/Identification_guide", new ArrayList<>());
        for (Dragon dragon: responseEjector.ejectNewDragon(response.getBody())){
            Optional<Dragon> optionalDragon = dragonService.findByName(dragon.getName());
            if(optionalDragon.isPresent()){
                continue;
            }
            dragonService.addDragon(dragon);
        }
        return "redirect:/admin";
    }
}