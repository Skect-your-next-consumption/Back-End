package com.hana.api.account.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Entity
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountAnalysis extends BaseEntity {

    @EmbeddedId
    private AccountAnalysisId id;


    @ManyToOne
    @MapsId("accountNum")
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
