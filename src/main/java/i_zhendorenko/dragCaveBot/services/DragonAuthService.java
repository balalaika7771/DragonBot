package i_zhendorenko.dragCaveBot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class DragonAuthService {

    @Value("${dragonCave.url.login}")
    String loginUrl;
    public List<String> auth(String username, String password){
        // Создание объекта RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Создание параметров запроса (логин и пароль)
        MultiValueMap<String, String> loginParams = new LinkedMultiValueMap<>();
        loginParams.add("username", username);
        loginParams.add("password", password);

        // Создание заголовков запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Создание объекта запроса
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(loginParams, headers);

        // Отправка POST-запроса для аутентификации
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        // Получение кук из заголовков ответа
        HttpHeaders responseHeaders = response.getHeaders();

        return responseHeaders.get(HttpHeaders.SET_COOKIE);
    }
}
