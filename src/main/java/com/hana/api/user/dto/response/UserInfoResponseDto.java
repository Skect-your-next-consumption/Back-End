package com.hana.api.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class UserInfoResponseDto {

    private String userName;
    private String userGender;
    private LocalDate userBirth;
    private String userPhone;
    private Integer userCredit;
    private String userAddress;
    private String userProfileUrl;
    private String userAccountNum;
}
