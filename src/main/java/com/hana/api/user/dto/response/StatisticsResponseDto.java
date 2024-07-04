package com.hana.api.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StatisticsResponseDto {
    private long analysis_cafe;
    private long analysis_food;
    private long analysis_total;
    private long etc;
    private long pleasure;
    private long transportation;
}
