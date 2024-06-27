package com.hana.api.user.entity;

import com.hana.api.account.entity.Account;
import com.hana.api.challenge.entity.ChallengeUsers;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.common.entity.BaseEntity;
import com.hana.common.type.Gender;
import com.hana.common.type.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@DynamicInsert
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

    @Column(name = "user_name_eng", length = 25, nullable = false)
    private String userNameEng;

    @Enumerated(EnumType.STRING)
    private Gender userGender;

    @Column(name = "user_birth")
    private LocalDate userBirth;

    @Column(name = "user_phone", length = 30)
    private String userPhone;

    @Column(name = "user_credit")
    @ColumnDefault("5")
    private Integer userCredit;

    @Column(name = "user_address", length = 100)
    private String userAddress;

    @Column(name = "user_profile", length = 300)
    @ColumnDefault("https://sync-bucket1.s3.ap-northeast-2.amazonaws.com/profile/default.png")
    private String userProfile;

    @Enumerated(EnumType.STRING)
    private Role userRole;

    @OneToOne
    @JoinColumn(name = "account_num")
    private Account account;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<PictureDiary> pictureDiaries;


    public void updateProfile(String ImageUrl){
        this.userProfile = ImageUrl;
    }

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ChallengeUsers> challengeUsers;

}
