package com.hana.api.challenge.service;

import com.hana.api.challenge.dto.request.ChallengeCreateRequest;
import com.hana.api.challenge.dto.request.InvitationInfo;
import com.hana.api.challenge.dto.request.InvitationListRequest;
import com.hana.api.challenge.dto.response.InvitationListResponse;
import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.challenge.entity.ChallengeUsersId;
import com.hana.api.challenge.repository.ChallengeRepository;
import com.hana.api.challenge.repository.ChallengeUsersRepository;
import com.hana.api.user.entity.User;
import com.hana.api.user.repository.UserRepository;
import com.hana.common.exception.ErrorCode;
import com.hana.common.response.Response;
import com.hana.common.type.State;
import com.hana.common.util.UuidGenerator;
import com.hana.config.security.jwt.JwtTokenProvider;
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
                            .challengeBase(inserted)
                            .build();
            challengeUsersRepository.save(challengeUsers);
        }
        return response.success("챌린지 생성 완료");
    }

    public ResponseEntity<?> getOngoingChallenges(User user){
        return response.success(challengeRepository.findAllChallengeByChallengeUsers_UserAndState(user,State.Active));
    }

    public ResponseEntity<?> getHotChallenges(){
        return response.success(challengeRepository.findTop3ByChallengeCategory().toArray(),HttpStatus.OK);
    }

    public ResponseEntity<?> getDoneChallenges(User user){
        return response.success(challengeRepository.findAllChallengeByChallengeUsers_UserAndState(user,State.Finish));
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
                        .build());

            }
        }
        return response.success(invitationListResponses.toArray(),HttpStatus.OK);
    }

    public ResponseEntity<?> getRecentList(User user){
        List<User> users = new ArrayList<>();
        List<Challenge> challenges = challengeRepository.findTop3ByChallengeUsers_UserOrderByCreatedDateDesc(user);
        for (int i=0;i<challenges.size();i++){
            List<ChallengeUsers> challengeUsers = challenges.get(i).getChallengeUsers();
            for (int j=0;j<challengeUsers.size();j++){
                if(!challengeUsers.get(j).getUser().getUserCode().equals(user.getUserCode()) && !users.contains(challengeUsers.get(j).getUser())){
                    users.add(challengeUsers.get(j).getUser());
                }
            }
        }
        return response.success(users);
    }
}
