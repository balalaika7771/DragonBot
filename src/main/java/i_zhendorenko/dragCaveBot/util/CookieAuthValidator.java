package i_zhendorenko.dragCaveBot.util;

import i_zhendorenko.dragCaveBot.models.Person;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


public class CookieAuthValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return List.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        List<String> cookies = (List<String>) o;

        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.COOKIE, cookies);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                "https://dragcave.net/dragons",
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        String responseBody = responseEntity.getBody();

        if(!responseBody.contains("<title>Your Dragons - Dragon Cave</title>")){
            errors.rejectValue("password", "", "не действителен");
        }
    }
}
