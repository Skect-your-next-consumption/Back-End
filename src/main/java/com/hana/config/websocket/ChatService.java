package com.hana.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public List<ChatRoom> findAll() {
        return chatRepository.findAll();
    }

    public Optional<ChatRoom> findRoomById(String roomId) {
        return chatRepository.findById(roomId);
    }

    public ChatRoom createRoom(String name) {
        String roomId = "5fb637aa-6854-460a-81b4-b0822392be42";
        ChatRoom chatRoom = ChatRoom.of(roomId, name);
        chatRepository.save(roomId, chatRoom);
        return chatRoom;
    }

    public void handleAction(
            String roomId,
            WebSocketSession session,
            ChatMessage chatMessage
    ) {
        Optional<ChatRoom> room = findRoomById(roomId);
        try {
        if (room.isEmpty()){
                session.sendMessage(Util.Chat.resolveTextMessage(ChatMessage.builder().messageType(ChatMessage.MessageType.NOTICE).message("존재하지 않는 방입니다.").build()));
                return;
        }
        if(isWelcome(chatMessage)){
            room.get().getSessions().stream().filter(s -> s.getId().equals(chatMessage.getTo())).forEach(s -> {
                try {
                    s.sendMessage(Util.Chat.resolveTextMessage(chatMessage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if (isEnterRoom(chatMessage)) {
            room.get().join(session);
            chatMessage.setMessage(chatMessage.getSender() + "님 환영합니다.");
        }

        TextMessage textMessage = Util.Chat.resolveTextMessage(chatMessage);
        room.get().sendMessage(textMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEnterRoom(ChatMessage chatMessage) {
        return chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER);
    }

    private boolean isWelcome(ChatMessage chatMessage) {
        return chatMessage.getMessageType().equals(ChatMessage.MessageType.WELCOME);
    }
}