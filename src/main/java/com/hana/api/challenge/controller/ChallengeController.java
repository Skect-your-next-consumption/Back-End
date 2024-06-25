package com.hana.api.challenge.controller;


import com.hana.api.challenge.dto.request.ChallengeCreateRequest;
import com.hana.api.challenge.service.ChallengeService;
import com.hana.api.user.dto.request.SignupRequest;
import com.hana.api.user.service.LoginService;
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
@RequestMapping("/api/challenges")
@Tag(name = "Challenge API", description = "챌린지 생성 / 조회 / 부가 기능을 담당하는 API")
public class ChallengeController {
    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 생성", description = "챌린지 생성을 위한 API 입니다.")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ChallengeCreateRequest challengeCreateRequest) {
        return challengeService.create(challengeCreateRequest);
    }
}
