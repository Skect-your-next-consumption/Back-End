package com.hana.api.login.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginRequest {

    private String user_id;  // 사용자 이름 또는 ID
    private String user_pwd;  // 비밀번호

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(user_id, user_pwd);
    }
}
