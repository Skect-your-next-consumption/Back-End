package com.hana.api.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeStartStatus {
    private String category;
    private String cost;
    private String date;
    private int goalAmount;
}
