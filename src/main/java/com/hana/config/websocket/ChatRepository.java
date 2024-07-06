package com.hana.config.websocket;

import lombok.RequiredArgsConstructor;
import com.hana.config.websocket.ChatRoom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ChatRepository {

    private final Map<String, ChatRoom> chatRooms;
    public void save(String roomId, ChatRoom chatRoom) {
        chatRooms.put(roomId, chatRoom);
    }

    public Optional<ChatRoom> findById(String roomId) {
        boolean isExist = chatRooms.containsKey(roomId);
        if(isExist){
            return Optional.of(chatRooms.get(roomId));
        }
        return Optional.empty();
    }

    public List<ChatRoom> findAll() {
        return new ArrayList<>(chatRooms.values());
    }
}