package com.hana.api.diary.controller;

import com.hana.api.diary.dto.request.DiaryCreateRequest;
import com.hana.api.diary.service.PictureDiaryService;
import com.hana.api.user.entity.User;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
@Tag(name = "PictureDiary API", description = "그림일기 API")
public class PictureDiaryController {
    private final PictureDiaryService pictureDiaryService;

    @Operation(summary = "그림일기 생성", description = "그림일기 생성을 위한 API 입니다.")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DiaryCreateRequest diaryCreateRequest, @CurrentUser User user) {
        log.info("diaryCreateRequest : {}", diaryCreateRequest.toString());
        log.info("user : {}", user.toString());
        return pictureDiaryService.create(diaryCreateRequest, user);
    }
}
