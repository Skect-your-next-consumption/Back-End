package com.hana.api.challenge.controller;


import com.hana.api.challenge.dto.request.ChallengeCreateRequest;
import com.hana.api.challenge.service.ChallengeService;
import com.hana.api.user.entity.User;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Operation(summary = "진행중인 챌린지 조회", description = "진행중인 챌린지를 조회하는 API 입니다.")
    @RequestMapping("/ongoing")
    public ResponseEntity<?> getOngoingChallenges(@CurrentUser User user) {
        return challengeService.getOngoingChallenges(user);
    }

    @Operation(summary = "완료된 챌린지 조회", description = "완료된 챌린지를 조회하는 API 입니다.")
    @RequestMapping("/done")
    public ResponseEntity<?> getDoneChallenges(@CurrentUser User user) {
        return challengeService.getDoneChallenges(user);
    }

    @Operation(summary = "챌린지 수 상위 3개 정보 조회", description = "챌린지 수 상위 3개 정보를 조회하는 API 입니다.")
    @RequestMapping("/hot")
    public ResponseEntity<?> getHotChallenges() {
        return challengeService.getHotChallenges();
    }
}
