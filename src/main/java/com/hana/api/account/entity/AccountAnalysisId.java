package com.hana.api.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountAnalysisId implements Serializable {

    @Column(name = "analysis_date")
    private LocalDate analysisDate;

    @Column(length = 40)
    private String accountNum;
}
