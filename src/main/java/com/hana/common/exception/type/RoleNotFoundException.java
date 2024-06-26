package com.hana.common.exception.type;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class RoleNotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public RoleNotFoundException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
