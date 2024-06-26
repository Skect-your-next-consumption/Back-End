package com.hana.api.challenge.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChallengeCreateRequest {
    private String challengeName;
    private int challengePeriod;
    private String challengeCategory;
    private Long challengeCost;
    private int challengeTargetAmount;
    private List<String> challengers;
//    private String role;
}