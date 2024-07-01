package com.hana.api.diary.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiaryUpdateRequest {
    private String diaryId;
    private String diaryTitle;
}
