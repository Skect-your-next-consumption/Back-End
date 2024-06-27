package com.hana.api.account.dto.response;

import com.hana.api.account.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class CardResponseDto {
    private String userNameEng;
    private String cardNum;
    private LocalDate cardExpiredDate;
    private String cardCvc;

    public CardResponseDto(Card card, String userNameEng){
        this.userNameEng = userNameEng;
        this.cardNum = card.getCardNum();
        this.cardExpiredDate = card.getCardExpiredDate();
        this.cardCvc = card.getCardCvc();
    }
}
