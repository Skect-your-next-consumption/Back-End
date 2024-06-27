package com.hana.api.user.controller;

import com.hana.api.user.dto.request.LoginRequest;
import com.hana.api.user.dto.request.ProfileRequest;
import com.hana.api.user.dto.request.SignUpRequest;
import com.hana.api.user.entity.User;
import com.hana.api.user.service.UserService;
import com.hana.common.type.CurrentUser;
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
@RequestMapping("/api/users")
@Tag(name = "User API", description = "User가 요청하는 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 API 입니다.")
    @PostMapping(value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@ModelAttribute SignUpRequest registerRequest) {
        return userService.signUp(registerRequest);
    }

    @Operation(summary = "로그인", description = "로그인을 위한 API 입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest authRequest) {
        log.info(authRequest.toString());
        return userService.signIn(authRequest);
    }

    @Operation(summary = "내 정보조회", description = "홈에서 내 정보를 조회하기 위한 API 입니다.")
    @PostMapping("/my-info")
    public ResponseEntity<?> getMyInfo(@CurrentUser User user) {
        return userService.getMyInfo(user);
    }

    @Operation(summary = "내 정보조회", description = "마이페이지에서 내 정보를 조회하기 위한 API 입니다.")
    @PostMapping("/my-page")
    public ResponseEntity<?> getMyPage(@CurrentUser User user) {
        return userService.getMyPage(user);
    }

    @Operation(summary = "회원 프로필 이미지 수정", description = "회원의 프로필 이미지를 수정하기 위한 API 입니다.")
    @PutMapping(value = "/my-profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProfile(@CurrentUser User user,
                                           @ModelAttribute ProfileRequest profileRequest) {
        return userService.updateProfile(user, profileRequest);
    }
}
