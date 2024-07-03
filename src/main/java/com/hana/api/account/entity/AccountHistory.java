package com.hana.api.account.entity;

import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory extends BaseEntity {

    @Id
    @Column(length = 40)
    private String historyCode;

    @ManyToOne
    @JoinColumn(name = "account_num", nullable = false)
    private Account account;

    private Integer historyAmount;

    @Column(length = 20)
    private String historyOpposit;

    private String historyClass;

    private Long historyBeforeBalance;

    private Long historyAfterBalance;

    private String historyCategory;
}
