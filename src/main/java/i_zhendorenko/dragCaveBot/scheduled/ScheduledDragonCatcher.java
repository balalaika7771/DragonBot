package i_zhendorenko.dragCaveBot.scheduled;


import i_zhendorenko.dragCaveBot.POJO.Code;
import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.CoolCode;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ScheduledDragonCatcher {
    @Value("#{'${dragonCave.url.caves}'.split(',')}")
    private List<String> urlList;
    CookieAuthService cookieAuthService;
    PersonService personService;
    HttpClientService httpClientService;
    DragonAuthService dragonAuthService;
    CoolCodeService coolCodeService;
    ResponseEjector responseEjector;


    @Scheduled(fixedDelay = 100000)
    public void Catch() {
        Iterable<Person> allPersons = personService.getAllPeople();
        for(Person person : allPersons){
            //получение куки для аутентификации
            Optional<CookieAuth> lastCookieAuth = cookieAuthService.getLastCookieAuthByPerson(person);
            if(lastCookieAuth.isEmpty()){
                continue;
            }
            List<String> cookies = lastCookieAuth.get().getCookies();

            //TODO обновление куки если они протухли или были отключены

            //получение шаблонов для кодов
            List<CoolCode> coolCodes = coolCodeService.getAllCodesByPerson(person);

            //TODO получение списка выбранных драконов


            //роходимся по сайтам
            for(String url : urlList){
                //получаем ответ от запроса
                ResponseEntity<String> Response = HttpClientService.sendGetRequest(url,cookies);

                //секция для сбора драконов с крутыми именами
                List<Code> codes = responseEjector.ejectCode(Response.getBody());
                for (Code code: codes){
                    for(CoolCode coolcode: coolCodes){
                        if(code.getSampleCode().contains(coolcode.getCode())){
                            System.out.println("Пойман - " + code);
                            HttpClientService.sendGetRequest(code.getUrl(),cookies);
                            break;
                        }
                    }
                }
                System.out.println(codes);
                //TODO секция для сбора интересных видов драконов
            }
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
    @Autowired
    public void setCoolCodeService(CoolCodeService coolCodeService) {
        this.coolCodeService = coolCodeService;
    }
}
