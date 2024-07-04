package com.hana.api.diary.dto.response;

import com.hana.api.account.dto.response.AccountLogResponse;
import com.hana.api.account.entity.AccountHistory;
import com.hana.common.type.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class DiaryInfoResponseDto {
    private LocalDateTime createdDate;
    private State state;
    private String diaryCode;
    private String diaryTitle;
    private String diaryImage;
    private Map<String, Object> diaryTags;
    private List<AccountLogResponse> diaryPayments;
    private String diaryConcept;
}
