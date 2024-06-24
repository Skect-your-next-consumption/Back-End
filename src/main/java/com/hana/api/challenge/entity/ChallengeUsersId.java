package com.hana.api.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeUsersId implements Serializable {

    @Column(length = 40)
    private String userCode;

    @Column(length = 40)
    private String challengeCode;
}
