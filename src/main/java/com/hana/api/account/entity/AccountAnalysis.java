package com.hana.api.account.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAnalysis extends BaseEntity {

    @Id
    private LocalDate analysisDate;

    @ManyToOne
    @JoinColumn(name = "account_num", nullable = false)
    @JsonBackReference
    private Account account;

    private Integer analysisTotal;
    private Integer analysisFood;
    private Integer pleasure;
    private Integer analysisCafe;
    private Integer transportation;
    private Integer etc;
}
