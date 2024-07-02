package com.hana.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.s3.S3DeleteException;
import com.hana.common.exception.s3.S3EssayUploadException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${s3.host.name}")
    private String hostName;

    private final AmazonS3 amazonS3;

    @PostConstruct
    public void init() {
        log.info("AmazonS3 instance: {}", amazonS3);
        log.info("Bucket: {}", bucket);
        log.info("HostName: {}", hostName);
    }

    public String uploadImage(MultipartFile image) {

        // s3에 저장할 파일명 생성
        String filename = image.getOriginalFilename();
        String s3FileName = "profile/" + UUID.randomUUID() + "_" + filename;

        // s3에 저장할 파일의 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        // s3 스토리지에 image 업로드
        try {
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), metadata);
        } catch (Exception e) {
            // image 업로드 에러 발생
            throw new S3EssayUploadException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
        // 업로드한 image의 s3 스토리지 경로
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public String updateImage(String profileUrl, MultipartFile image) {

        deleteFileFromBucket(decodeURL(profileUrl));

        // s3에 저장할 파일명 생성
        String filename = image.getOriginalFilename();
        String s3FileName = "profile/" + UUID.randomUUID() + "_" + filename;

        // s3에 저장할 파일의 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        // s3 스토리지에 image 업로드
        try {
            amazonS3.putObject(bucket, s3FileName, image.getInputStream(), metadata);
        } catch (Exception e) {
            // image 업로드 에러 발생
            throw new S3EssayUploadException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }
        // 업로드한 image의 s3 스토리지 경로
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void deleteFileFromBucket(String fileUrl) {
        String decodeURL = decodeURL(fileUrl);
        boolean fileExists = amazonS3.doesObjectExist(bucket, decodeURL);
        // S3 버킷에서 파일 삭제
        if (fileExists) {
            amazonS3.deleteObject(bucket, decodeURL);
        }
    }

    private String decodeURL(String fileUrl) {
        // fileUrl에서 hostName 제거, 모든 space 제거, UTF8 형식으로 디코딩
        return URLDecoder.decode(fileUrl.replace(hostName, "")
                .replaceAll("\\p{Z}", ""), StandardCharsets.UTF_8);
    }
}
