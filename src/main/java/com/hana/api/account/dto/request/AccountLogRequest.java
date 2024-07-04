package com.hana.api.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLogRequest {
    @Schema(description = "결제 상대", example = "엽기떡볶이")
    private String historyOpposit;
    @Schema(description = "결제 금액", example = "20000")
    private Integer historyAmount;
    @Schema(description = "결제 분류", example = "식비")
    private String historyClass;
    @Schema(description = "결제 카테고리", example = "음식점")
    private String historyCategory;
}
