package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomRepository, Long> {

    Optional<ChatRoom> findByChatRoomId(Long chatRoomId);
}
