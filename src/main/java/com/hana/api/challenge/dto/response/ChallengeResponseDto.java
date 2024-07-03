package com.hana.api.challenge.dto.response;

import com.hana.api.challenge.entity.Challenge;
import com.hana.api.challenge.entity.ChallengeUsers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChallengeResponseDto {
    private Challenge challenge;
    private ChallengeUsers me;
}
