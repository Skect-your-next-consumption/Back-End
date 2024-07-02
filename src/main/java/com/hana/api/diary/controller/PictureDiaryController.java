package com.hana.api.diary.controller;

import com.hana.api.diary.dto.request.DiaryCreateRequest;
import com.hana.api.diary.dto.request.DiaryUpdateRequest;
import com.hana.api.diary.service.PictureDiaryService;
import com.hana.api.user.entity.User;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "그림일기 제목 수정", description = "그림일기 제목 수정을 위한 API 입니다.")
    @PutMapping("/title")
    public ResponseEntity<?> updateDiaryTitle(@RequestBody DiaryUpdateRequest diaryCreateRequest, @CurrentUser User user) {
        return pictureDiaryService.updateDiaryTitle(diaryCreateRequest.getDiaryId(), diaryCreateRequest.getDiaryTitle(), user);
    }

    @Operation(summary = "그림일기 목록 조회", description = "그림일기 목록 조회를 위한 API 입니다.")
    @GetMapping("/list")
    public ResponseEntity<?> getDiaryList(@CurrentUser User user) {
        return pictureDiaryService.getDiaryList(user);
    }

    @Operation(summary = "그림일기 상세 조회", description = "그림일기 상세 조회를 위한 API 입니다.")
    @GetMapping("/info")
    public ResponseEntity<?> getDiaryInfo(@RequestParam("diaryCode") String diaryCode, @CurrentUser User user) {
        return pictureDiaryService.getDiaryInfo(diaryCode, user);
    }
}
