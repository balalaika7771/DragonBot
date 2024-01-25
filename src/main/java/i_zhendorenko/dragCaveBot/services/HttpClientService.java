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

    public static ResponseEntity<String> sendGetRequest(String url,List<String> cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.addAll("Cookie", cookies);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        return responseEntity;
    }
}
