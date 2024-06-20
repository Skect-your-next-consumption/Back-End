package com.hana.api.login.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String user_id;
    private String user_pwd;
//    private String role;
}