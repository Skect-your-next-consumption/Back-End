package com.hana.api.login.controller;

import com.hana.api.login.dto.request.LoginRequest;
import com.hana.api.login.dto.request.SignupRequest;
import com.hana.api.login.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
@Tag(name = "Login API", description = "유저 인증, 인가를 위한 API")
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 API 입니다.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest registerRequest) {
        return loginService.signUp(registerRequest);
    }

    @Operation(summary = "로그인", description = "로그인을 위한 API 입니다.")
    @PostMapping("")
    public ResponseEntity<?> createConsultantAuthToken(@RequestBody LoginRequest authRequest) {
        log.info(authRequest.toString());
        return loginService.signIn(authRequest);
    }
}
