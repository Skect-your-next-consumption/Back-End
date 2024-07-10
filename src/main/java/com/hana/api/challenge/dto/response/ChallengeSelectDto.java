package com.hana.api.challenge.dto.response;

import com.hana.api.user.dto.response.UserForChallengeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChallengeSelectDto {
    private String name;
    private List<UserForChallengeDto> users;
    private boolean aiPick;
}
