package com.hana.common.type;

import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.type.RoleNotFoundException;

import java.util.Arrays;

public enum Gender {

    Male("남자"),
    Female("여자");

    private String value;

    Gender(String state) {
        this.value = state;
    }

    public String getValue(){
        return value;
    }

    public static Gender getGender(String gender) {
        return Arrays.stream(Gender.values())
                .filter(r -> r.getValue().equals(gender))
                .findAny()
                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.GENDER_NOT_FOUND));
    }
}
