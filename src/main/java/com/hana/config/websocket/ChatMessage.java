package com.hana.config.websocket;

import com.hana.api.challenge.dto.response.ChallengeChangeStatus;
import com.hana.api.challenge.dto.response.ChallengeCreateStatus;
import com.hana.api.user.dto.response.UserForChallengeDto;
import com.hana.api.user.entity.User;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK,NOTICE,WELCOME,CHANGE
    }

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String to;
    private String message;
    private UserForChallengeDto user;
    private ChallengeCreateStatus challengeCreateStatus;
    private ChallengeChangeStatus challengeChangeStatus;
}