package com.hana.common.exception.s3;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class S3DeleteException extends RuntimeException{
    private ErrorCode errorCode;

    public S3DeleteException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
