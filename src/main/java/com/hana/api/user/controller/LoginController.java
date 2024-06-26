package com.hana.api.user.controller;

import com.hana.api.user.dto.request.LoginRequest;
import com.hana.api.user.dto.request.SignupRequest;
import com.hana.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
@Tag(name = "Login API", description = "유저 인증, 인가를 위한 API")
public class LoginController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 API 입니다.")
    @PostMapping(value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@ModelAttribute SignupRequest registerRequest) {
        return userService.signUp(registerRequest);
    }

    @Operation(summary = "로그인", description = "로그인을 위한 API 입니다.")
    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
        log.info(authRequest.toString());
        return userService.signIn(authRequest);
    }
}
