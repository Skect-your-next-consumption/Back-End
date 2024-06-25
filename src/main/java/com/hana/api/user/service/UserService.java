package com.hana.api.user.service;

import com.hana.api.account.service.AccountService;
import com.hana.api.user.dto.request.LoginRequest;
import com.hana.api.user.dto.request.SignupRequest;
import com.hana.api.user.dto.response.LoginResponseDto;
import com.hana.api.user.entity.User;
import com.hana.api.user.repository.UserRepository;
import com.hana.common.exception.user.NameDuplicateException;
import com.hana.common.response.Response;
import com.hana.common.exception.ErrorCode;
import com.hana.common.type.Gender;
import com.hana.common.type.Role;
import com.hana.common.util.ImageUploader;
import com.hana.common.util.UuidGenerator;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Response response;

    public ResponseEntity<?> signUp(SignupRequest signupRequest){

        String imageUrl = imageUploader.uploadImage(signupRequest.getImage());

        if(userRepository.existsByUserId(signupRequest.getUserId())){
            throw new NameDuplicateException(ErrorCode.USER_NAME_DUPLICATION);
        }

        User user =
                User.builder()
                        .userCode(UuidGenerator.generateUuid())
                        .userId(signupRequest.getUserId())
                        .userPwd(passwordEncoder.encode(signupRequest.getUserPwd()))
                        .userName(signupRequest.getUserName())
                        .userGender(Gender.getGender(signupRequest.getUserGender()))
                        .userBirth(signupRequest.getUserBirth())
                        .userPhone(signupRequest.getUserPhone())
                        .userCredit(signupRequest.getUserCredit())
                        .userAddress(signupRequest.getUserAddress())
                        .userRole(Role.getRole(signupRequest.getUserRole()))
                        .userProfile(imageUrl)
                        .account(accountService.createAccount(signupRequest.getAccountName(), signupRequest.getAccountBalance()))
                        .build();

        userRepository.save(user);

        return response.success("회원가입 완료");
    }

    public ResponseEntity<?> signIn(LoginRequest loginRequest) {

        if (userRepository.findByUserId(loginRequest.getUserId()).isEmpty()) {
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