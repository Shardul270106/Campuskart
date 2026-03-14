package com.campuskart.demo.repositories;

import com.campuskart.demo.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByBuyerUserIdAndSellerUserIdAndItemId(
            Long buyerUserId, Long sellerUserId, Long itemId
    );

    List<ChatRoom> findBySellerUserId(Long sellerId);
}
