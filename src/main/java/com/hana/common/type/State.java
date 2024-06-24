package com.hana.common.type;

import com.hana.common.exception.ErrorCode;
import com.hana.common.exception.type.StateNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum State {

    Active("Active"),
    Finish("Finish"),
    Delete("Delete");

    private String value;

    State(String state) {
        this.value = state;
    }

    public String getValue(){
        return value;
    }

    public static State getState(String state) {
        return Arrays.stream(State.values())
                .filter(s -> s.getValue().equals(state))
                .findAny()
                .orElseThrow(() -> new StateNotFoundException(ErrorCode.STATE_NOT_FOUND));
    }
}
