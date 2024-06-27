package com.hana.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequest {
    @Schema(description = "유저 로그인 아이디", example = "gyun1712")
    private String userId;
    @Schema(description = "유저 패스워드", example = "1234!!")
    private String userPwd;
    @Schema(description = "유저 이름", example = "김서윤")
    private String userName;
    @Schema(description = "유저 성별", example = "여자")
    private String userGender;
    @Schema(description = "유저 생년월일", example = "1998-08-28")
    private LocalDate userBirth;
    @Schema(description = "유저 전화번호", example = "01034333292")
    private String userPhone;
    @Schema(description = "유저의 주소", example = "성남시 분당구 미금로 177")
    private String userAddress;
    @Schema(description = "유저 권한", example = "user")
    private String userRole;
    @Schema(description = "유저 프로필 이미지", type = "string", format = "binary")
    private MultipartFile image;

    @Schema(description = "생성 계좌명", example = "하나 S20 주거래통장")
    private String accountName;
    @Schema(description = "초기 입금 금액", example = "30000")
    private Long accountBalance;
}