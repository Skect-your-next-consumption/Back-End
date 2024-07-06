package com.hana.config.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoom {
    private final String roomId;
    private final String name;
    private WebSocketSession master;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name, WebSocketSession master) {
        this.roomId = roomId;
        this.name = name;
    }

    public void sendMessage(TextMessage message) {
        this.getSessions()
                .parallelStream()
                .forEach(session -> sendMessageToSession(session, message));
    }

    private void sendMessageToSession(WebSocketSession session, TextMessage message) {
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void join(WebSocketSession session) {
        if(sessions.isEmpty()){
            this.master = session;
        }else{
            try {
                this.master.sendMessage(new TextMessage(session + "님이 입장하셨습니다."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        sessions.add(session);
    }

    public static ChatRoom of(String roomId, String name) {
        return ChatRoom.builder()
                .roomId(roomId)
                .name(name)
                .build();
    }
}