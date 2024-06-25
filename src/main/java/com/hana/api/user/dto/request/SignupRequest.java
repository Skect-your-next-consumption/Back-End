package com.hana.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignupRequest {
    private String userId;
    private String userPwd;
    private String userName;
    private String userGender;
    private LocalDate userBirth;
    private String userPhone;
    private String userAddress;
    private String userProfile;
//    private String role;
}