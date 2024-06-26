package com.hana.api.account.entity;

import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @Column(length = 20, name = "account_num")
    private String accountNum;

    @Column(length = 20)
    private String accoundCard;

    private Long accountBalance;

    @Column(length = 20)
    private String accountName;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountHistory> accountHistories;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountAnalysis> accountAnalyses;
}
