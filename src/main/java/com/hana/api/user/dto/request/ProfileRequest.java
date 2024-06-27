package com.hana.api.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileRequest {
    @Schema(description = "유저 프로필 이미지", type = "string", format = "binary")
    private MultipartFile image;
}