package com.hana.api.account.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;

@Entity
@Data
@Builder
@ToString
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AccountAnalysis extends BaseEntity {

    @EmbeddedId
    private AccountAnalysisId id;


    @ManyToOne
    @MapsId("accountNum")
    @JoinColumn(name = "account_num", nullable = false)
    @JsonBackReference
    private Account account;

    @ColumnDefault("0")
    private Integer analysisTotal;
    @ColumnDefault("0")
    private Integer analysisFood;
    @ColumnDefault("0")
    private Integer pleasure;
    @ColumnDefault("0")
    private Integer analysisCafe;
    @ColumnDefault("0")
    private Integer transportation;
    @ColumnDefault("0")
    private Integer etc;

    public void UpdateAnalysis(Integer amount, String category){
        switch (category){
            case "식비":
                this.analysisFood += amount;
                break;
            case "유흥":
                this.pleasure += amount;
                break;
            case "카페":
                this.analysisCafe += amount;
                break;
            case "교통":
                this.transportation += amount;
                break;
            default:
                this.etc += amount;
                break;
        }
        this.analysisTotal += amount;
    }
}
