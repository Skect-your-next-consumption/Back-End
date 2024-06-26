package com.hana.common.exception.user;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
