package i_zhendorenko.dragCaveBot.scheduled;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.POJO.DragonPOJO;
import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Dragon;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.*;
import i_zhendorenko.dragCaveBot.util.CookieAuthValidator;
import i_zhendorenko.dragCaveBot.util.ResponseEjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ScheduledDragonCatcher {
    @Value("${dragonCave.catchFlag}")
    static String catchFlag;
    @Value("#{'${dragonCave.url.caves}'.split(',')}")
    private List<String> urlList;
    private final CookieAuthService cookieAuthService;

    private final CookieAuthValidator cookieAuthValidator;
    private final PersonService personService;
    private final CoolCodeService coolCodeService;
    private final ResponseEjector responseEjector;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledDragonCatcher.class);

    private  final  DragonService dragonService;
    public ScheduledDragonCatcher(CookieAuthService cookieAuthService, PersonService personService, HttpClientService httpClientService, DragonAuthService dragonAuthService, CookieAuthValidator cookieAuthValidator, CoolCodeService coolCodeService, ResponseEjector responseEjector, DragonService dragonService) {
        this.cookieAuthService = cookieAuthService;
        this.personService = personService;
        this.cookieAuthValidator = cookieAuthValidator;
        this.coolCodeService = coolCodeService;
        this.responseEjector = responseEjector;
        this.dragonService = dragonService;
    }
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional(readOnly = true)
    public void InFiveMinutes(){
        Catch();
    }
    @Scheduled(fixedDelay = 10000)
    @Transactional(readOnly = true)
    public void Catch() {
        Iterable<Person> allPersons = personService.getAllPeople();
        for(Person person : allPersons){
            //получение куки для аутентификации
            Optional<CookieAuth> lastCookieAuth = cookieAuthService.getLastCookieAuthByPerson(person);
            if(lastCookieAuth.isEmpty()){
                continue;
            }
            List<String> cookies = lastCookieAuth.get().getCookies();

            //получение шаблонов для кодов
            List<CoolCode> coolCodes = coolCodeService.getAllCodesByPerson(person);

            //получение интересных видов
            List<Dragon> dragonsForPerson = personService.getDragonsForPerson(person.getId());
            //List<Dragon> dragonsForPerson = personService.findDragonsById(person.getId());

            //проходимся по сайтам c яйцами
            for(String url : urlList){
                //получаем ответ от запроса
                ResponseEntity<String> Response = HttpClientService.sendGetRequest(url,cookies);

                //секция для сбора драконов с крутыми именами
                catchCoolCode(person, Response, coolCodes, cookies);

                //Сбор интересных видов дракончиков
                catchDragon(person, Response, dragonsForPerson, cookies);
            }
        }
    }

    private void catchDragon(Person person, ResponseEntity<String> Response, List<Dragon> dragonsForPerson, List<String> cookies) {
        if(dragonsForPerson.isEmpty()){
            return;
        }
        List<DragonPOJO> dragonList = responseEjector.ejectDragon(Response.getBody());
        //  System.out.println(dragonList);
        for (DragonPOJO dragon : dragonList) {
            if(dragonsForPerson
                    .stream()
                    .anyMatch(pDragom -> pDragom.getName().equals(dragon.getName()))){
                System.out.println("Catch - " + dragon + " for " + person.getUsername());
                logger.info("Catch by dragon - " + dragon + " for " + person.getUsername());
                catchThis(cookies, dragon.getUrl());

            }
        }
    }

    @NotNull
    private static boolean catchThis(List<String> cookies, String url) {
        ResponseEntity<String> response = HttpClientService.sendGetRequest(url, cookies);
        return response.getBody().contains(catchFlag);
    }

    private void catchCoolCode(Person person, ResponseEntity<String> Response, List<CoolCode> coolCodes, List<String> cookies) {
        if(coolCodes.isEmpty()){
            return;
        }
        List<Code> codeList = responseEjector.ejectCode(Response.getBody());
        System.out.println(codeList);
        for (Code code: codeList){
            if(coolCodes
                    .stream()
                    .anyMatch(coolcode->code.getSampleCode().contains(coolcode.getCode()))){
                System.out.println("Catch - " + code + " for " + person.getUsername());
                logger.info("Catch by code - " + code + " for " + person.getUsername());
                 catchThis(cookies, code.getUrl());
            }
        }
    }



}
