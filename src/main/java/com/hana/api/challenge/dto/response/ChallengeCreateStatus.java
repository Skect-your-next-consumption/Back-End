package com.hana.api.challenge.dto.response;

import com.hana.api.user.dto.response.UserForChallengeDto;
import com.hana.api.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChallengeCreateStatus {
    private List<UserForChallengeDto> users;
    private List<ChallengeSelectDto> categoryPicks;
    private List<ChallengeSelectDto> costPicks;
    private List<ChallengeSelectDto> periodPicks;
}
