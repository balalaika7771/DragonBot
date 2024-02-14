package i_zhendorenko.dragCaveBot.scheduled;
import i_zhendorenko.dragCaveBot.models.*;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.POJO.DragonPOJO;
import i_zhendorenko.dragCaveBot.services.*;
import i_zhendorenko.dragCaveBot.util.CookieAuthValidator;
import i_zhendorenko.dragCaveBot.util.ResponseEjector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ScheduledDragonCatcher {
    @Value("${dragonCave.catchFlag}")
    String catchFlag;
    @Value("#{'${dragonCave.url.caves}'.split(',')}")
    private List<String> urlList;
    private final CookieAuthService cookieAuthService;

    private final CookieAuthValidator cookieAuthValidator;
    private final PersonService personService;
    private final CoolCodeService coolCodeService;
    private final ResponseEjector responseEjector;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledDragonCatcher.class);
    private final LogRecordService logRecordService;
    private  final  DragonService dragonService;
    public ScheduledDragonCatcher(CookieAuthService cookieAuthService, PersonService personService, HttpClientService httpClientService, DragonAuthService dragonAuthService, CookieAuthValidator cookieAuthValidator, CoolCodeService coolCodeService, ResponseEjector responseEjector, LogRecordService logRecordService, DragonService dragonService) {
        this.cookieAuthService = cookieAuthService;
        this.personService = personService;
        this.cookieAuthValidator = cookieAuthValidator;
        this.coolCodeService = coolCodeService;
        this.responseEjector = responseEjector;
        this.logRecordService = logRecordService;
        this.dragonService = dragonService;
    }
    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional()
    public void InFiveMinutes(){
        catching();
    }


    @Scheduled(fixedDelay = 10000)
    @Transactional()
    public void catching() {

        List<String> adminCookies = cookieAuthService.getLastCookieAuthByPerson(personService.findByUsername("pupukaka").get()).get().getCookies();

        for(String url : urlList){
            //получаем ответ от запроса
            ResponseEntity<String> Response = HttpClientService.sendGetRequest(url,adminCookies);
            List<DragonPOJO> dragonList = responseEjector.ejectDragon(Response.getBody());
            System.out.println(dragonList);
            dragonList.
                    forEach(dragonPOJO -> personService
                            .findPersonsByDragon(dragonPOJO.getDragon())
                            .forEach(person -> {
                                cookieAuthService.getLastCookieAuthByPerson(person).ifPresent(cookieAuth -> {
                                    if(catchThis(cookieAuth.getCookies(), dragonPOJO.getUrl())){
                                        System.out.println("Catch - " + dragonPOJO + " for " + person.getUsername());
                                        logger.info("Catch by dragon - " + dragonPOJO + " for " + person.getUsername());
                                        logRecordService.saveLogRecord(new LogRecord(person,dragonPOJO.getDragon()));
                                    }
                                });
                            }));



            List<Code> codeList = responseEjector.ejectCode(Response.getBody());
            System.out.println(codeList);
            Iterable<Person> allPersons = personService.getAllPeople();
            for(Person person : allPersons) {
                for (Code code : codeList) {
                    if (person.getCodes()
                            .stream()
                            .anyMatch(coolcode -> code.getSampleCode().contains(coolcode.getCode()))) {
                        if (catchThis(cookieAuthService.getLastCookieAuthByPerson(person).get().getCookies(), code.getUrl())){
                            System.out.println("Catch - " + code + " for " + person.getUsername());
                            logger.info("Catch by code - " + code + " for " + person.getUsername());
                        }
                    }
                }

            }
        }
    }



    @NotNull
    private boolean catchThis(List<String> cookies, String url) {
        ResponseEntity<String> response = HttpClientService.sendGetRequest(url, cookies);
        return Objects.requireNonNull(response.getBody()).contains(catchFlag);
    }




}
