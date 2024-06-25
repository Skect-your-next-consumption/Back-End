package com.hana.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum ErrorCode {

    // Type
    STATE_NOT_FOUND("S001", "등록된 State가 아닙니다."),
    ROLE_NOT_FOUND("R001", "등록된 Role이 아닙니다."),
    GENDER_NOT_FOUND("G001", "등록된 Gender가 아닙니다."),

    // User
    USER_NOT_FOUND("U001","등록된 회원이 아닙니다."),
    USER_NOT_AUTHENTICATION("U002", "인증된 회원이 아닙니다."),
    USER_EMAIL_DUPLICATION("U003","중복된 이메일입니다."),
    USER_NAME_DUPLICATION("U004","중복된 이름입니다."),
    USER_UNAUTHORIZED("U005", "인증에 실패했습니다."),

    // Token
    INVALID_REFRESHTOKEN("U005", "유효하지 않은 리프레쉬 토큰 입니다."),
    NOT_FOUND_REFRESHTOKEN("U006", "리프레쉬 토큰이 존재하지 않습니다."),
    INVALID_ACCESSTOKEN("U007", "유효하지 않은 액세스 토큰 입니다."),
    NOT_FOUND_ACCESSTOKEN("U008", "액세스 토큰이 존재하지 않습니다."),

    // Account
    ACCOUNT_NUM_DUPLICATION("A001", "중복된 계좌번호입니다."),

    // S3
    IMAGE_UPLOAD_FAIL("S001", "이미지 업로드에 실패했습니다."),
    IMAGE_DELETE_FAIL("S002", "이미지 삭제에 실패했습니다")
    ;

    private final String errorCode;
    private final String message;

    @JsonValue
    public ErrorDetails toJson() {
        return new ErrorDetails(errorCode, message);
    }

    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        @JsonProperty("errorCode")
        private final String errorCode;
        @JsonProperty("message")
        private final String message;
    }
}
