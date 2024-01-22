package i_zhendorenko.dragCaveBot.scheduled;


import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.CookieAuthService;
import i_zhendorenko.dragCaveBot.services.DragonAuthService;
import i_zhendorenko.dragCaveBot.services.HttpClientService;
import i_zhendorenko.dragCaveBot.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ScheduledDragonCatcher {


    CookieAuthService cookieAuthService;
    PersonService personService;
    HttpClientService httpClientService;
    @Scheduled(fixedDelay = 10000)
    public void CatchWithCoolCode() {
        Iterable<Person> allPersons = personService.getAllPeople();
        for(Person person : allPersons){
            Optional<CookieAuth> lastCookieAuth = cookieAuthService.getLastCookieAuthByPerson(person);
            if(lastCookieAuth.isEmpty()){
                continue;
            }
            List<String> cookies = lastCookieAuth.get().getCookies();
            System.out.println(httpClientService.sendRequestWithCookies("https://dragcave.net/locations/5-alpine",cookies));

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
}
