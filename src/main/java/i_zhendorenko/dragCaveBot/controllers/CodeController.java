package i_zhendorenko.dragCaveBot.controllers;

import i_zhendorenko.dragCaveBot.DTO.CodeDTO;
import i_zhendorenko.dragCaveBot.models.Code;
import i_zhendorenko.dragCaveBot.security.PersonDetails;
import i_zhendorenko.dragCaveBot.services.CodeService;
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
import java.util.stream.Collectors;

@Controller
public class CodeController {

    private final CodeService codeService;

    public CodeController( CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/code")
    public String showStrings(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ошибка
            return "redirect:/main";
        }

        // Используем объект Pageable для указания параметров пагинации
        PageRequest pageable = PageRequest.of(page, size);

        // Получаем страницу объектов CodeDTO
        Page<CodeDTO> codePage = codeService.getAllCodesByPersonPaged(((PersonDetails) principal).getPerson(), pageable)
                .map(CodeDTO::new);

        model.addAttribute("codeList", codePage.getContent()); // Список текущей страницы
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", codePage.getTotalPages());
        return "code/code";
    }

    @PostMapping("/code/add")
    public String addString(@NotNull @NotEmpty String newString) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ощибка
            return "code/code";
        }
        codeService.addCode(new Code(newString.trim(),((PersonDetails)principal).getPerson()));
        return "redirect:/code";
    }

    @PostMapping("/code/delete")
    public String deleteString(@ModelAttribute("codeDTO") CodeDTO icode) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PersonDetails)) {
            //TODO ощибка
            return "code/code";
        }

        List<Code> codeList = codeService.getAllCodesByPerson(((PersonDetails)principal).getPerson());

        codeList.stream().
                filter(icode::equals)
                .forEach(codeService::deleteCode);
        return "redirect:/code";
    }
}
