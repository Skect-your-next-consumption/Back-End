package com.hana.common.exception.account;

import com.hana.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PaymentFailedException extends RuntimeException{
    private ErrorCode errorCode;

    public PaymentFailedException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
