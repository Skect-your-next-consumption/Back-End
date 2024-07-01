package com.hana.api.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAnalysisId implements Serializable {

    @Column(name = "analysis_date")
    private LocalDate analysisDate;

    @Column(length = 40)
    private String accountNum;
}
