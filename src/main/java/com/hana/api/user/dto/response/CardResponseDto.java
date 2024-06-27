package com.hana.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class CardResponseDto {
    private String cardNum;
    private LocalDate cardExpiredDate;
    private String cardCvc;
}
