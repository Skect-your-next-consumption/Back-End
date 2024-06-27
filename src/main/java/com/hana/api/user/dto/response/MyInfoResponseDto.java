package com.hana.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class MyInfoResponseDto {
    private String userName;
    private Integer userCredit;
    private Integer userChallenges;
    private String userNameEng;
    private String accountNum;
    private CardResponseDto card;
}
