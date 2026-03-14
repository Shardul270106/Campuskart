package com.campuskart.demo.service;

import com.campuskart.demo.dto.ChatMessageDto;
import com.campuskart.demo.models.ChatMessage;
import com.campuskart.demo.repositories.ChatMessageRepository;
import com.campuskart.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository msgRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepo userRepo; // <-- add this

    public void saveAndBroadcast(ChatMessageDto dto) {

        String senderName = userRepo.findById(dto.getSenderUserId())
                .map(u -> u.getUsername())
                .orElse("Unknown");

        ChatMessage msg = new ChatMessage();
        msg.setChatRoomId(dto.getChatRoomId());
        msg.setSenderUserId(dto.getSenderUserId());
        msg.setContent(dto.getContent());
        msg.setSenderName(senderName);


        ChatMessage saved = msgRepo.save(msg);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + dto.getChatRoomId(),
                saved
        );
    }
}
