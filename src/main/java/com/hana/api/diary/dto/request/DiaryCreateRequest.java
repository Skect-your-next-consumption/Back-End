package com.hana.api.diary.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DiaryCreateRequest {
    private List<String> diaryPayments;
    private List<String> diaryTags;
    private String diaryConcept;
}
