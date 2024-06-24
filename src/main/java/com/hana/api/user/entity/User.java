package com.hana.api.user.entity;

import com.hana.api.account.entity.Account;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @Column(name = "user_code", length = 40, nullable = false)
    private String userCode;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Column(name = "user_pwd", length = 100, nullable = false)
    private String userPwd;

    @Column(name = "user_name", length = 25, nullable = false)
    private String userName;

    @Column(name = "user_gender", length = 25)
    private String userGender;

    @Column(name = "user_birth")
    private LocalDateTime userBirth;

    @Column(name = "user_phone", length = 30)
    private String userPhone;

    @Column(name = "user_credit")
    private Integer userCredit;

    @Column(name = "user_address", length = 100)
    private String userAddress;

    @Column(name = "user_profile", length = 100)
    private String userProfile;

    @OneToOne
    @JoinColumn(name = "account_num")
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PictureDiary> pictureDiaries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChallengeUsers> challengeUsers;
}
