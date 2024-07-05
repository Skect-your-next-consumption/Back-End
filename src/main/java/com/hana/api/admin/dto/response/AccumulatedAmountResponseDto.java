package com.hana.api.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccumulatedAmountResponseDto {
    private LocalDate date;
    private String accumulatedAmount;
    private String addAmount;
    private String refundedAmount;
}
