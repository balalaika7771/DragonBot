package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.DTO.CoolCodeDTO;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import i_zhendorenko.dragCaveBot.services.CoolCodeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
public class CoolCodeController {

    private final CoolCodeService coolCodeService;

    public CoolCodeController(CoolCodeService coolCodeService) {
        this.coolCodeService = coolCodeService;
    }

    @GetMapping("/coolCode")
    public String showStrings(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ошибка
            return "redirect:/main";
        }

        // Используем объект Pageable для указания параметров пагинации
        PageRequest pageable = PageRequest.of(page, size);

        // Получаем страницу объектов CodeDTO
        Page<CoolCodeDTO> coolCodePage = coolCodeService.getAllCodesByPersonPaged(((PersonDetails) principal).getPerson(), pageable)
                .map(CoolCodeDTO::new);

        model.addAttribute("coolCodeList", coolCodePage.getContent()); // Список текущей страницы
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coolCodePage.getTotalPages());
        return "code/code";
    }

    @PostMapping("/coolCode/add")
    public String addString(@NotNull @NotEmpty String newString) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ощибка
            return "code/code";
        }
        String[] wordsArray = newString.split(" ");

        // Выводим результат
        for (String word : wordsArray) {
            coolCodeService.addCode(new CoolCode(word.trim(),((PersonDetails)principal).getPerson()));

        }
         return "redirect:/coolCode";
    }

    @PostMapping("/coolCode/delete")
    public String deleteString(@ModelAttribute("coolCode") CoolCodeDTO icode) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ощибка
            return "code/code";
        }

        List<CoolCode> coolCodeList = coolCodeService.getAllCodesByPerson(((PersonDetails)principal).getPerson());

        coolCodeList.stream().
                filter(icode::equals)
                .forEach(coolCodeService::deleteCode);
        return "redirect:/coolCode";
    }
}
