package com.hana.common.util;


import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class GetPaymentCategory {
    @Value("${kakao.secret}")
    private String kakaoSecret;
    public String getPaymentCategory(String payment) throws IOException, InterruptedException, ParseException {
        String encodedInputValue = URLEncoder.encode(payment, StandardCharsets.UTF_8);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://dapi.kakao.com/v2/local/search/keyword.json?query=" + encodedInputValue))
                    .header("Authorization", "KakaoAK " + kakaoSecret)
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());
        log.info("jsonObject: " + jsonObject.toString());
        JSONArray documents = (JSONArray) jsonObject.get("documents");
        JSONObject firstDocument = (JSONObject) documents.get(0);

        return firstDocument.get("category_name").toString();
    }
}
