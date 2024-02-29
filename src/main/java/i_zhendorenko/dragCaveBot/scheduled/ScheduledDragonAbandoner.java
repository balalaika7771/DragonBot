package i_zhendorenko.dragCaveBot.scheduled;


import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.CookieAuthService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import i_zhendorenko.dragCaveBot.util.ResponseEjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ScheduledDragonAbandoner {

    private final CookieAuthService cookieAuthService;

    private final PersonService personService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledDragonAbandoner.class);

    private final ResponseEjector responseEjector;
    public ScheduledDragonAbandoner(CookieAuthService cookieAuthService, PersonService personService, ResponseEjector responseEjector) {
        this.cookieAuthService = cookieAuthService;
        this.personService = personService;
        this.responseEjector = responseEjector;
    }

    @Scheduled(cron = "0 0 * * * *") // Каждый час
    @Transactional()
    public void abandon() {
        Optional<List<Person>> personList = personService.findByAbandonTrue();
        if (personList.isEmpty()){
            return;
        }

        for (Person person : personList.get()){

            List<String> cookies = cookieAuthService.getLastCookieAuthByPerson(person).get().getCookies();
            List<Code> eggs = responseEjector.ejectdragonName(cookies);
            eggs = eggs.stream()
                    .filter(code -> person.getCodes()
                            .stream().noneMatch(coolCode -> code.getSampleCode()
                                    .contains(coolCode.getCode())))
                    .collect(Collectors.toList());
            for (Code egg : eggs){
                // Создание объекта HttpHeaders и установка кук
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.addAll("Cookie", cookies);
                headers.set("Referer", "https://dragcave.net/actions/" + egg + "/abandon");
                // Создание объекта RestTemplate
                RestTemplate restTemplate = new RestTemplate();

                // Создание объекта для тела запроса (здесь параметр "p")
                MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
                map.add("p", person.getPassword());
                // Создание объекта для запроса
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                String url = "https://dragcave.net/actions/" + egg + "/abandon";
                // Отправка POST-запроса
                ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                System.out.println("Response abandon action: " + response.getStatusCode());
                logger.info("abandon - " + egg + " for " + person.getUsername());

            }
        }
    }
}
