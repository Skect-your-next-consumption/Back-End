package com.hana.api.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginRequest {

    private String userId;  // 사용자 이름 또는 ID
    private String userPwd;  // 비밀번호

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, userPwd);
    }
}
