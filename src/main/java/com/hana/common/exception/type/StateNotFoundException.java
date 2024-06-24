package com.hana.common.exception.type;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class StateNotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public StateNotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
