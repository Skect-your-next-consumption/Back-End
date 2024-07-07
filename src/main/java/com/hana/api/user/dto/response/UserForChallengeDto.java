package com.hana.api.user.dto.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserForChallengeDto {
    private String userCode;
    private String userName;
    private String userPhone;
    private String userProfile;
    private boolean master;
}
