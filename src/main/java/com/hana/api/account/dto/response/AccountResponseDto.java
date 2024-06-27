package com.hana.api.account.dto.response;

import com.hana.api.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AccountResponseDto {
    private String accountNum;
    private Long accountBalance;
    private String accountName;
    private CardResponseDto card;

    public AccountResponseDto(Account account, String userNameEng){
        this.accountNum = account.getAccountNum();
        this.accountName = account.getAccountName();
        this.accountBalance = account.getAccountBalance();
        this.card = new CardResponseDto(account.getCard(), userNameEng);
    }
}
