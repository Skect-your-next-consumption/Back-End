package com.hana.api.challenge.entity;

import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeUsers extends BaseEntity {

    @EmbeddedId
    private ChallengeUsersId id;

    @ManyToOne
    @MapsId("userCode")
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;

    @ManyToOne
    @MapsId("challengeCode")
    @JoinColumn(name = "challenge_code", referencedColumnName = "challenge_code")
    private Challenge challenge;

    private Boolean challengeUserResult;

    private Integer challengeUserSpentMoney;
}
