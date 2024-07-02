package com.hana.api.user.dto.response;

import com.hana.api.account.dto.response.CardResponseDto;
import com.hana.api.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class MyPageResponseDto {

    private String userName;
    private String userGender;
    private LocalDate userBirth;
    private String userPhone;
    private Integer userCredit;
    private String userAddress;
    private String userProfileUrl;
    private String userEngName;
    private Account account;
    private int ChallengeCount;
}
