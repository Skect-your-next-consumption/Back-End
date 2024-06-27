package com.hana.api.challenge.dto.query;

import com.hana.api.challenge.entity.Challenge;
import com.hana.common.type.State;

import java.time.LocalDateTime;

public interface OngoingChallengeInterface {
    Challenge getChallenge();
    Boolean getChallengeUserResult();
    Integer getChallengeUserSpentMoney();
}
