package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.DTO.CoolCodeDTO;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import i_zhendorenko.dragCaveBot.services.CoolCodeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api/coolCode")
public class CoolCodeController {

    private final CoolCodeService coolCodeService;

    public CoolCodeController(CoolCodeService coolCodeService) {
        this.coolCodeService = coolCodeService;
    }

    @GetMapping
    public Page<CoolCodeDTO> showStrings(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ошибка доступа");
        }

        PageRequest pageable = PageRequest.of(page, size);
        return coolCodeService.getAllCodesByPersonPaged(((PersonDetails) principal).getPerson(), pageable)
                .map(CoolCodeDTO::new);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addString(@RequestBody String newCoolCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ошибка доступа");
        }
        String[] wordsArray = newCoolCode.split(" ");
        for (String word : wordsArray) {
            coolCodeService.addCode(new CoolCode(word.trim(),((PersonDetails)principal).getPerson()));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteString(@RequestBody List<Integer> ids) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ошибка доступа");
        }

        List<CoolCode> coolCodeList = coolCodeService.getAllCodesByPerson(((PersonDetails)principal).getPerson());
        coolCodeList.stream()
                .filter(coolCode -> ids.contains(coolCode.getId()))
                .forEach(coolCodeService::deleteCode);
        return ResponseEntity.ok().build();
    }
}
