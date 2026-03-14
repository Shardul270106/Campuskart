package com.campuskart.demo.controllers;

import com.campuskart.demo.models.ChatMessage;
import com.campuskart.demo.models.ChatRoom;
import com.campuskart.demo.repositories.ChatMessageRepository;
import com.campuskart.demo.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins="*")
public class ChatRestController {

    @Autowired
    private ChatRoomRepository roomRepo;

    @Autowired
    private ChatMessageRepository messageRepo;

    // ===============================
    // BUYER OPENS CHAT → CREATE OR GET ROOM
    // ===============================
    @PostMapping("/room")
    public ChatRoom getOrCreateRoom(
            @RequestParam Long buyerUserId,
            @RequestParam Long sellerUserId,
            @RequestParam Long itemId) {

        return roomRepo
                .findByBuyerUserIdAndSellerUserIdAndItemId(buyerUserId, sellerUserId, itemId)
                .orElseGet(() -> {
                    ChatRoom room = new ChatRoom();
                    room.setBuyerUserId(buyerUserId);
                    room.setSellerUserId(sellerUserId);
                    room.setItemId(itemId);
                    return roomRepo.save(room);
                });
    }

    // ===============================
    // SELLER CLICKS "VIEW CHAT"
    // ===============================
    @GetMapping("/room/find")
    public ResponseEntity<?> findRoom(
            @RequestParam Long user1,
            @RequestParam Long user2,
            @RequestParam Long itemId) {

        return roomRepo
                .findByBuyerUserIdAndSellerUserIdAndItemId(user1, user2, itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    // ===============================
    // LOAD MESSAGE HISTORY
    // ===============================
    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        return messageRepo.findByChatRoomIdOrderByTimestampAsc(roomId);
    }

    // ===============================
    // SELLER: LIST ALL BUYER CHATS
    // ===============================
    @GetMapping("/rooms/seller/{sellerId}")
    public List<ChatRoom> sellerRooms(@PathVariable Long sellerId) {
        return roomRepo.findBySellerUserId(sellerId);
    }
}
