package i_zhendorenko.dragCaveBot.services;

import i_zhendorenko.dragCaveBot.models.CookieAuth;
import i_zhendorenko.dragCaveBot.models.Person;
import i_zhendorenko.dragCaveBot.repositories.CookieAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CookieAuthService {

    private final CookieAuthRepository cookieAuthRepository;

    @Autowired
    public CookieAuthService(CookieAuthRepository cookieAuthRepository) {
        this.cookieAuthRepository = cookieAuthRepository;
    }

    public CookieAuth saveCookieAuth(Person person, List<String> stringList) {
        CookieAuth cookieAuth = new CookieAuth();
        cookieAuth.setPerson(person);
        cookieAuth.setCookies(stringList);
        return cookieAuthRepository.save(cookieAuth);
    }


    public Optional<CookieAuth> getLastCookieAuthByPerson(Person person) {
        return cookieAuthRepository.findTopByPersonOrderByIdDesc(person);
    }
}
