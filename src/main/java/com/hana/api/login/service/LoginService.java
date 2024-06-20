package com.hana.api.login.service;

import com.hana.api.login.dto.request.LoginRequest;
import com.hana.api.login.dto.request.SignupRequest;
import com.hana.api.login.dto.response.LoginResponseDto;
import com.hana.api.login.entity.User;
import com.hana.api.login.repository.UserRepository;
import com.hana.common.response.Response;
import com.hana.common.exception.ErrorCode;
import com.hana.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Response response;

    public ResponseEntity<?> signUp(SignupRequest signupRequest){

        User user =
                User.builder()
                        .userCode("124123")
                        .userId(signupRequest.getUser_id())
                        .userPwd(passwordEncoder.encode(signupRequest.getUser_pwd()))
//                        .role(signupRequest.getRole())
                        .build();

        userRepository.save(user);

        return response.success("회원가입 완료");
    }

    public ResponseEntity<?> signIn(LoginRequest loginRequest) {

        if (userRepository.findByUserId(loginRequest.getUser_id()).isEmpty()) {
            return response.fail(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        try {
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
            log.info("[UsernamePasswordAuthenticationToken] : {}", authenticationToken );

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            log.info(authentication.toString());

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            LoginResponseDto loginResponseDto = jwtTokenProvider.generateToken(authentication);
            log.info("[signIn] 토큰 발급 : {}", loginResponseDto.toString());

            // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
            //log.info("RT:" + authentication.getName() + " : " + authResponseDto.getRefreshToken() + " : " + TimeUnit.MILLISECONDS);
            // redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

            return response.success(loginResponseDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return response.fail(ErrorCode.USER_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }
}
