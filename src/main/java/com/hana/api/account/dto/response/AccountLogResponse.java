package com.hana.api.account.dto.response;

import com.hana.api.account.entity.AccountHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccountLogResponse {
    private String historyOpposit;
    private Long historyAmount;
    private Integer historyBusinessCode;
    private Long historyBeforeBalance;
    private Long historyAfterBalance;
    private LocalDate historyDate;

    public AccountLogResponse(AccountHistory accountHistory){
        this.historyOpposit = accountHistory.getHistoryOpposit();
        this.historyAmount = accountHistory.getHistoryAmount();
        this.historyBusinessCode = accountHistory.getHistoryBusinessCode();
        this.historyBeforeBalance = accountHistory.getHistoryBeforeBalance();
        this.historyAfterBalance = accountHistory.getHistoryAfterBalance();
        this.historyDate = accountHistory.getCreatedDate().toLocalDate();
    }
}
