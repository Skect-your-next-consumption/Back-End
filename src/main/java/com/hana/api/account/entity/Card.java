package com.hana.api.account.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hana.api.account.dto.response.CardResponseDto;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    @Id
    @Column(length = 20, name = "card_num")
    private String cardNum;

    @Column(length = 20)
    private LocalDate cardExpiredDate;

    @Column(length = 3)
    private String cardCvc;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Account account;

    public CardResponseDto getCardResponseDto(){
        return CardResponseDto.builder()
                .cardNum(this.cardNum)
                .cardExpiredDate(this.cardExpiredDate)
                .cardCvc(this.cardCvc)
                .build();
    }
}
