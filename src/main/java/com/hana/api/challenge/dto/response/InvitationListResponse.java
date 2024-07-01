package com.hana.api.challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InvitationListResponse {
    private String name;
    private String phoneNum;
    private String realName;
}
