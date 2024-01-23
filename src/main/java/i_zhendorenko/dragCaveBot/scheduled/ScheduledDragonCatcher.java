package i_zhendorenko.dragCaveBot.scheduled;


import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ScheduledDragonCatcher {
    @Value("#{'${dragonCave.url.caves}'.split(',')}")
    private List<String> urlList;
    CookieAuthService cookieAuthService;
    PersonService personService;
    HttpClientService httpClientService;
    DragonAuthService dragonAuthService;

    ResponseEjector responseEjector;
    @Scheduled(fixedDelay = 10000)
    public void CatchWithCoolCode() {
        Iterable<Person> allPersons = personService.getAllPeople();
        for(Person person : allPersons){
            Optional<CookieAuth> lastCookieAuth = cookieAuthService.getLastCookieAuthByPerson(person);
            if(lastCookieAuth.isEmpty()){
                continue;
            }
            List<Code> codes = new LinkedList<Code>();
            List<String> cookies = lastCookieAuth.get().getCookies();
            for(String url : urlList){
                String Response = httpClientService.sendRequestWithCookies(url,cookies);
                codes.addAll(responseEjector.ejectCode(Response)) ;
            }
            //TODO обновление куки если они протухли или были отключены

            System.out.println(codes);

        }
    }
    @Autowired
    public void setCookieAuthService(CookieAuthService cookieAuthService) {
        this.cookieAuthService = cookieAuthService;
    }
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    @Autowired
    public void setHttpClientService(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }
    @Autowired
    public void setDragonAuthService(DragonAuthService dragonAuthService) {
        this.dragonAuthService = dragonAuthService;
    }
    @Autowired
    public void setResponseEjector(ResponseEjector responseEjector) {
        this.responseEjector = responseEjector;
    }
}
