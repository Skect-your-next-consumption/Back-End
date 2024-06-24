package com.hana.common.exception.account;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AccountNumDuplicateException extends RuntimeException{
    private ErrorCode errorCode;

    public AccountNumDuplicateException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
