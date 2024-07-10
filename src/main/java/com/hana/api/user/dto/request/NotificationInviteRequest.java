package com.hana.api.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationInviteRequest {
    private String roomId;
    private String prefix;
    private List<String> userCodes;
}
