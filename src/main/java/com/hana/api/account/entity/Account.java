package com.hana.api.account.entity;

import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j

@Entity
@Table(name = "accounts")
@Data
@Builder
@ToString
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

//    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private User user;

    @OneToOne
    @JoinColumn(name = "card_num")
    private Card card;

    public Long updateAccountBalance(Long amount){
        this.accountBalance -= amount;
        log.info("\n\n\n\n\n\n\n\namount:"+amount.toString());
        return this.accountBalance;
    }
}
