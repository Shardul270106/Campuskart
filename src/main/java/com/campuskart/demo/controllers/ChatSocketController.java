package com.campuskart.demo.controllers;

import com.campuskart.demo.dto.ChatMessageDto;
import com.campuskart.demo.models.ChatMessage;
import com.campuskart.demo.repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    @Autowired
    private ChatMessageRepository messageRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(@Payload ChatMessageDto dto) {

        ChatMessage msg = new ChatMessage();
        msg.setChatRoomId(dto.getChatRoomId());
        msg.setSenderUserId(dto.getSenderUserId());
        msg.setSenderName(dto.getSenderName());
        msg.setContent(dto.getContent());

        messageRepo.save(msg);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + dto.getChatRoomId(),
                msg
        );
    }

}
