package i_zhendorenko.dragCaveBot.POJO;

import java.util.List;

public class MyHttpResponse {
    private String responseBody;
    private List<String> receivedCookies;

    public MyHttpResponse(String responseBody, List<String> receivedCookies) {
        this.responseBody = responseBody;
        this.receivedCookies = receivedCookies;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public List<String> getReceivedCookies() {
        return receivedCookies;
    }
}