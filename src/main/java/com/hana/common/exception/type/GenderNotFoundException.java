package com.hana.common.exception.type;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class GenderNotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public GenderNotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
