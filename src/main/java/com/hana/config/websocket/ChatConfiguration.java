package com.hana.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ChatConfiguration {

    @Bean
    public Map<String, ChatRoom> chatRooms(){
        return new ConcurrentHashMap<>();
    }
}
