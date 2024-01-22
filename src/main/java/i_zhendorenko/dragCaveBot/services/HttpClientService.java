package i_zhendorenko.dragCaveBot.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HttpClientService {
    public String sendRequestWithCookies(String url,List<String> cookies) {
        // Установка заголовков запроса, включая куки
        HttpHeaders headers = new HttpHeaders();
        headers.addAll("Cookie", cookies);

        // Создание объекта запроса с заголовками
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Создание объекта RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Отправка GET-запроса с использованием куки
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url, // URL вашего запроса
                HttpMethod.GET,
                requestEntity,
                String.class);
        // Получение тела ответа
        String responseBody = responseEntity.getBody();

        return responseBody;
    }
}
