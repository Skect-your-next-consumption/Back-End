package com.hana.api.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hana.common.entity.BaseEntity;
import com.hana.common.type.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenge", indexes = @Index(name = "idx_challenge_state",columnList = "state"))
public class Challenge extends BaseEntity {

    @Id
    @Column(length = 40, name = "challenge_code")
    private String challengeCode;

    @Column(length = 40, name="challenge_name")
    private String challengeName;

    private Integer challengePeriod;

    @Column(length = 50)
    private String challengeCategory;


    private Long challengeCost;

    private Integer challengeTargetAmount;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChallengeUsers> challengeUsers;
}
