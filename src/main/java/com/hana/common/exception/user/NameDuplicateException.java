package com.hana.common.exception.user;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NameDuplicateException extends RuntimeException{

    private ErrorCode errorCode;

    public NameDuplicateException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}