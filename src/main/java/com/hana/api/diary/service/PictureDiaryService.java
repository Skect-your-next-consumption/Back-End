package com.hana.api.diary.service;

import com.hana.api.diary.dto.request.DiaryCreateRequest;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.diary.repository.PictureDiaryRepository;
import com.hana.api.user.entity.User;
import com.hana.common.response.Response;
import com.hana.common.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureDiaryService {

    private final Response response;
    private final PictureDiaryRepository pictureDiaryRepository;

    public ResponseEntity<?> create(DiaryCreateRequest diaryCreateRequest, User user) {
        PictureDiary pictureDiary = PictureDiary.builder()
                .diaryCode(UuidGenerator.generateUuid())
                .diaryTitle(diaryCreateRequest.getDiaryTitle())
                .diaryConcept(diaryCreateRequest.getDiaryConcept())
                .diaryTags(Map.of("tags", diaryCreateRequest.getDiaryTags()))
                .diaryPayments(Map.of("payments", diaryCreateRequest.getDiaryPayments()))
                .user(user)
                .build();
        log.info(pictureDiary.toString());
        return response.success(pictureDiaryRepository.save(pictureDiary));
    }
}
