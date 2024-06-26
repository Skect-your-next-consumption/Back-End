package com.hana.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MyInfoResponseDto {
    private String userName;
    private Integer userCredit;
    private Integer userChallenges;
}
