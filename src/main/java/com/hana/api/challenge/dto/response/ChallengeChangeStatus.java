package com.hana.api.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeChangeStatus {
    private String statusClass;
    private String statusBefore;
    private String statusAfter;
}
