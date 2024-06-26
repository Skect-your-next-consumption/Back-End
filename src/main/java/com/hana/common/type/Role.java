package com.hana.common.type;

import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.type.RoleNotFoundException;
import com.hana.common.exception.type.StateNotFoundException;

import java.util.Arrays;

public enum Role {

    User("user"),
    Admin("admin");

    private String value;

    Role(String state) {
        this.value = state;
    }

    public String getValue(){
        return value;
    }

    public static Role getRole(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getValue().equals(role))
                .findAny()
                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.ROLE_NOT_FOUND));
    }
}
