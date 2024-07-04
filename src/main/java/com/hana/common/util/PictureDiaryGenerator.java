package com.hana.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.user.entity.User;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.s3.S3EssayUploadException;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureDiaryGenerator {

    @Resource(name = "getOpenAiService")
    private final OpenAiService openAiService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${s3.host.name}")
    private String hostName;

    @Value("${openai.secret}")
    private String openAiSecret;

    private final AmazonS3 amazonS3;
    public String generatePictureDiary(PictureDiary pictureDiary, User user) throws IOException, InterruptedException {

        String prompt = pictureDiary.getDiaryConcept().toString() + ", 단순하게      " + pictureDiary.getDiaryTags().get("tags").toString() + "라는 키워드들을 바탕으로 간단한 스토리를 만들고, 배경은 한국이야. 그 스토리를 바탕으로 그림 그려줘. 스토리는 그림에 적지 말고 너만 알고있어.";
        log.info("prompt : {}", prompt);

        String url = "https://api.openai.com/v1/images/generations";

        // JSON 문자열 생성
        String requestBody = String.format(
                "{\"model\":\"dall-e-3\",\"prompt\":\"%s\",\"n\":1,\"size\":\"1024x1024\"}",
                prompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAiSecret)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 본문에서 URL 추출
        String responseBody = response.body();
        int startIndex = responseBody.indexOf("https://"); // URL이 "https://"로 시작함
        int endIndex = responseBody.indexOf("\"", startIndex); // URL이 큰 따옴표로 끝남
        String imageUrl = responseBody.substring(startIndex, endIndex);

        log.info(response.body());
        log.info("===============");
        log.info(imageUrl);

        URL imageURL = new URL(imageUrl);



        // s3에 저장할 파일명 생성
        String s3FileName = "diary/" + user.getUserId() + "_" + pictureDiary.getDiaryTitle();

          // s3에 저장할 파일의 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength((long)imageURL.openStream().readAllBytes().length);
        metadata.setContentType("image/png");


//
//         s3 스토리지에 image 업로드
        try {
            amazonS3.putObject(bucket, s3FileName, imageURL.openStream(), metadata);
        } catch (Exception e) {
            // image 업로드 에러 발생
            e.printStackTrace();
            throw new S3EssayUploadException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
        // 업로드한 image의 s3 스토리지 경로
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
}
