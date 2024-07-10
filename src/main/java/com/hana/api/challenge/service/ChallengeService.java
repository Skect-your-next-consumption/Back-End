package com.hana.api.challenge.service;

import com.hana.api.challenge.dto.request.ChallengeCreateRequest;
import com.hana.api.challenge.dto.request.InvitationInfo;
import com.hana.api.challenge.dto.request.InvitationListRequest;
import com.hana.api.challenge.dto.request.MaxAmountRequest;
import com.hana.api.challenge.dto.response.ChallengeResponseDto;
import com.hana.api.challenge.dto.response.InvitationListResponse;
import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.challenge.entity.ChallengeUsersId;
import com.hana.api.challenge.repository.ChallengeRepository;
import com.hana.api.challenge.repository.ChallengeUsersRepository;
import com.hana.api.user.entity.User;
import com.hana.api.user.repository.UserRepository;
import com.hana.api.user.service.UserService;
import com.hana.common.exception.ErrorCode;
import com.hana.common.response.Response;
import com.hana.common.type.State;
import com.hana.common.util.UuidGenerator;
import com.hana.config.security.jwt.JwtTokenProvider;
import com.hana.config.websocket.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeUsersRepository challengeUsersRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Response response;
    private final ChatService chatService;
    private final UserService userService;
    private final ChallengeUsersService challengeUsersService;


    public ResponseEntity<?> create(ChallengeCreateRequest challengeCreateRequest){
        Challenge challenge =
                Challenge.builder()
                        .challengeCode(UuidGenerator.generateUuid())
                        .challengeName(challengeCreateRequest.getChallengeName())
                        .challengePeriod(challengeCreateRequest.getChallengePeriod())
                        .challengeCategory(challengeCreateRequest.getChallengeCategory())
                        .challengeCost(challengeCreateRequest.getChallengeCost())
                        .challengeTargetAmount(challengeCreateRequest.getChallengeTargetAmount())

                        .build();
        Challenge inserted =  challengeRepository.save(challenge);
        List<String> userIds = challengeCreateRequest.getChallengers();
        for (int i=0;i<userIds.size();i++){
            Optional<User> user = userRepository.findById(userIds.get(i));
            if(user.isEmpty()){
                return response.fail(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            ChallengeUsersId challengeUsersId = new ChallengeUsersId();
            challengeUsersId.setChallengeCode(inserted.getChallengeCode());
            challengeUsersId.setUserCode(user.get().getUserCode());
            ChallengeUsers challengeUsers =
                    ChallengeUsers.builder()
                            .id(challengeUsersId)
                            .challenge(inserted)
                            .user(user.get())
                            .build();
            challengeUsersRepository.save(challengeUsers);
        }
        return response.success(inserted.getChallengeCode());
    }

    public ResponseEntity<?> openChallenge(User user){
        return response.success(chatService.createRoom(user.getUserName()));
    }

    public ResponseEntity<?> getOngoingChallenges(User user){
        List<ChallengeResponseDto> challengeResponseDtos = new ArrayList<>();
        List<Challenge> challenges = challengeRepository.findAllChallengeByChallengeUsers_UserAndStateOrderByCreatedDateDesc(user, State.Active);
        for(int i=0;i<challenges.size();i++){
            Challenge challenge = challenges.get(i);
            ChallengeResponseDto challengeResponseDto = ChallengeResponseDto.builder()
                            .challenge(challenge)
                           .me(challenge.getChallengeUsers().stream().filter(challengeUsers -> challengeUsers.getUser().getUserCode().equals(user.getUserCode())).findFirst().get())
                            .build();
            challengeResponseDtos.add(challengeResponseDto);
        }
        return response.success(challengeResponseDtos);
    }

    public ResponseEntity<?> getDoneChallenges(User user){
        List<ChallengeResponseDto> challengeResponseDtos = new ArrayList<>();
        List<Challenge> challenges = challengeRepository.findAllChallengeByChallengeUsers_UserAndStateOrderByCreatedDateDesc(user, State.Finish);
        for(int i=0;i<challenges.size();i++){
            Challenge challenge = challenges.get(i);
            ChallengeResponseDto challengeResponseDto = ChallengeResponseDto.builder()
                    .challenge(challenge)
                    .me(challenge.getChallengeUsers().stream().filter(challengeUsers -> challengeUsers.getUser().getUserCode().equals(user.getUserCode())).findFirst().get())
                    .build();
            challengeResponseDtos.add(challengeResponseDto);
        }
        return response.success(challengeResponseDtos);
    }

    public ResponseEntity<?> getHotChallenges(){
        return response.success(challengeRepository.findTop3ByChallengeCategory().toArray(),HttpStatus.OK);
    }



    public ResponseEntity<?> getInvitationList(User user, InvitationListRequest invitationListRequest){
        List<InvitationInfo> invitationInfos = invitationListRequest.getInvitationList();
        List<InvitationListResponse> invitationListResponses = new ArrayList<>();
        for (int i=0;i<invitationInfos.size();i++){
            InvitationInfo info = invitationInfos.get(i);
            Optional<User> availableUser = userRepository.findByUserPhone(info.getPhoneNum());
            if(availableUser.isPresent()){
                invitationListResponses.add(InvitationListResponse.builder()
                        .name(info.getName())
                        .phoneNum(info.getPhoneNum())
                        .realName(availableUser.get().getUserName())
                        .imgUrl(availableUser.get().getUserProfile())
                        .userCode(availableUser.get().getUserCode())
                        .build());
            }
        }
        return response.success(invitationListResponses.toArray(),HttpStatus.OK);
    }

    public ResponseEntity<?> getRecentList(User user){
        List<InvitationListResponse> users = new ArrayList<>();
        List<Challenge> challenges = challengeRepository.findTop3ByChallengeUsers_UserOrderByCreatedDateDesc(user);
        for (int i=0;i<challenges.size();i++){
            List<ChallengeUsers> challengeUsers = challenges.get(i).getChallengeUsers();
            for (int j=0;j<challengeUsers.size();j++){
                if(!challengeUsers.get(j).getUser().getUserCode().equals(user.getUserCode())){
                    InvitationListResponse ilr =  InvitationListResponse.builder()
                            .name(challengeUsers.get(j).getUser().getUserName())
                            .phoneNum(challengeUsers.get(j).getUser().getUserPhone())
                            .imgUrl(challengeUsers.get(j).getUser().getUserProfile())
                            .userCode(challengeUsers.get(j).getUser().getUserCode())
                            .build();
                    if (!users.stream().anyMatch(user1 -> user1.getUserCode().equals(ilr.getUserCode()))){
                        users.add(ilr);
                    }
                }
            }
        }
        return response.success(users);
    }

    public ResponseEntity<?> getChallengeDetail(String challengeCode, User user){
        Optional<Challenge> challenge = challengeRepository.findById(challengeCode);
        if(challenge.isEmpty()){
            return response.fail(ErrorCode.CHALLENGE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        ChallengeResponseDto challengeResponseDto = ChallengeResponseDto.builder()
                .challenge(challenge.get())
                .me(challenge.get().getChallengeUsers().stream().filter(challengeUsers->challengeUsers.getUser().getUserCode().equals(user.getUserCode())).findFirst().get())
                .build();
        return response.success(challengeResponseDto);
    }

    public ResponseEntity<?> getChallengeStatisticsByGender(){
        return response.success(challengeRepository.getChallengeStatisticsByGender());
    }

    public ResponseEntity<?> getChallengeStatisticsByRatio(){
        return response.success(challengeRepository.getChallengeSuccessRatioByCategory());
    }

    public ResponseEntity<?> getMaxAmount(MaxAmountRequest maxAmountRequest) {
        return response.success(challengeRepository.getMaxAmount(maxAmountRequest.getIds()));
    }
    public void rewardChallenge(){
        List<Challenge> rewardTarget = challengeRepository.findActiveChallengesEndingToday();
        if (!rewardTarget.isEmpty()) {
            rewardTarget.forEach(challengeUsersService::findChallengeUsers);
        }
    }
}
