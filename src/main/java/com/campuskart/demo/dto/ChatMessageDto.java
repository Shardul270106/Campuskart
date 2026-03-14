package com.campuskart.demo.dto;

import lombok.Data;

@Data
public class ChatMessageDto {
    public Integer chatRoomId;
    public Integer senderUserId;
    public String senderName;
    public String content;
}
