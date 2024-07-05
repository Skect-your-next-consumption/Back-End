package com.hana.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.user.entity.User;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.s3.S3EssayUploadException;
import com.theokanning.openai.image.CreateImageRequest;
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

//        String prompt = pictureDiary.getDiaryConcept().toString() + ", 단순하게      " + pictureDiary.getDiaryTags().get("tags").toString() + "를 키워드로 그려줘";
//        log.info("prompt : {}", prompt);
//
//        String url = "https://api.openai.com/v1/images/generations";
//
//        // JSON 문자열 생성
//        String requestBody = String.format(
//                "{\"model\":\"dall-e-3\",\"prompt\":\"%s\",\"n\":1,\"size\":\"1024x1024\"}",
//                prompt);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + openAiSecret)
//                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        // 응답 본문에서 URL 추출
//        String responseBody = response.body();
//        int startIndex = responseBody.indexOf("https://"); // URL이 "https://"로 시작함
//        int endIndex = responseBody.indexOf("\"", startIndex); // URL이 큰 따옴표로 끝남
//        String imageUrl = responseBody.substring(startIndex, endIndex);
//
//        log.info(response.body());
//        log.info("===============");
//        log.info(imageUrl);

        String imageUrl = "https://sync-bucket1.s3.ap-northeast-2.amazonaws.com/result.png";

        URL imageURL = new URL(imageUrl);



        // s3에 저장할 파일명 생성
        String s3FileName = "diary/" + user.getUserId() + "_" + pictureDiary.getDiaryTitle();

          // s3에 저장할 파일의 메타데이터 생성


        Image srcImg = ImageIO.read(imageURL);
        Image mark_img = ImageIO.read(new URL("https://sync-bucket1.s3.ap-northeast-2.amazonaws.com/SyncMark.png"));
        int width = srcImg.getWidth(null);
        int height = srcImg.getHeight(null);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(srcImg, 0, 0, width, height, null);
        log.info(width + " " + height);
        int mark_img_width = mark_img.getWidth(null);
        int mark_img_height = mark_img.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));

        // 우측 하단에 워터마크 표시
        g.drawImage(mark_img, 0, (height - mark_img_height), mark_img_width, mark_img_height, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] bytes = new ByteArrayInputStream(baos.toByteArray()).readAllBytes();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType("image/png");






//         s3 스토리지에 image 업로드
        try {
            amazonS3.putObject(bucket, s3FileName,new ByteArrayInputStream(bytes), metadata);
        } catch (Exception e) {
            // image 업로드 에러 발생
            e.printStackTrace();
            throw new S3EssayUploadException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
        // 업로드한 image의 s3 스토리지 경로
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }
}
