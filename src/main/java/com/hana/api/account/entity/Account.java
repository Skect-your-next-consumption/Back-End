package com.hana.api.account.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @Column(length = 20, name = "account_num")
    private String accountNum;

    private Long accountBalance;

    @Column(length = 20)
    private String accountName;

//    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<AccountHistory> accountHistories;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @OneToOne
    @JoinColumn(name = "card_num")
    private Card card;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccountAnalysis> accountAnalyses = new ArrayList<>();

    public Long updateAccountBalance(Long amount){
        this.accountBalance -= amount;
        return this.accountBalance;
    }
}
