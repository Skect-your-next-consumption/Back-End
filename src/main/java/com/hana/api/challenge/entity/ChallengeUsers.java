package com.hana.api.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hana.api.user.entity.User;
import com.hana.common.entity.BaseEntity;
import com.hana.common.type.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeUsers {

    @EmbeddedId
    private ChallengeUsersId id;

    @ManyToOne
    @MapsId("userCode")
    @JoinColumn(name = "user_code", referencedColumnName = "user_code")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("challengeCode")
    @JoinColumn(name = "challenge_code", referencedColumnName = "challenge_code")
    @JsonBackReference
    private Challenge challenge;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state", referencedColumnName = "state")
    @JoinColumn(name = "created_date", referencedColumnName = "created_date")
    @JsonBackReference
    private Challenge challengeBase;
    
    private Boolean challengeUserResult;

    private Integer challengeUserSpentMoney;

}
