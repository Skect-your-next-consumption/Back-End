package com.hana.api.challenge.controller;


import com.hana.api.challenge.dto.request.ChallengeCreateRequest;
import com.hana.api.challenge.dto.request.InvitationListRequest;
import com.hana.api.challenge.service.ChallengeService;
import com.hana.api.user.entity.User;
import com.hana.common.type.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "챌린지 방 열기", description = "챌린지 방을 열기 위한 API 입니다.")
    @PostMapping("/opening")
    public ResponseEntity<?> openChallenge(@CurrentUser User user) {
        return challengeService.openChallenge(user);
    }

    @Operation(summary = "초대 가능 유저 조회", description = "챌린지 생성 시 초대 가능한 유저를 조회하는 API 입니다.")
    @PostMapping("/invitation-list")
    public ResponseEntity<?> getInvitationList(@CurrentUser User user, @RequestBody InvitationListRequest invitationListRequest){
        return challengeService.getInvitationList(user,invitationListRequest);
    }

    @Operation(summary = "최근 챌린지 같이 진행한 유저 조회", description = "최근 챌린지 같이 진행한 유저를 조회하는 API 입니다.")
    @RequestMapping("/recent-list")
    public ResponseEntity<?> getRecentList(@CurrentUser User user){
        return challengeService.getRecentList(user);
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

    @GetMapping("/detail")
    public ResponseEntity<?> getChallengeDetail(@RequestParam("challengeCode") String challengeCode){
        return challengeService.getChallengeDetail(challengeCode);
    }

    @GetMapping("/statistics/category/gender")
    public ResponseEntity<?> getChallengeStatisticsByGender(){
        return challengeService.getChallengeStatisticsByGender();
    }
}
