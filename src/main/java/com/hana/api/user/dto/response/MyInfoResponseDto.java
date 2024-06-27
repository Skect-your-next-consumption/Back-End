package com.hana.api.user.dto.response;

import com.hana.api.account.dto.response.CardResponseDto;
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
