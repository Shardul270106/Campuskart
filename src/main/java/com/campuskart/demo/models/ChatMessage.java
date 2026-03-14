package com.campuskart.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer chatRoomId;
  private Integer senderUserId;
  private String senderName;

  @Column(length = 2000)
  private String content;

  private LocalDateTime timestamp = LocalDateTime.now();
}
