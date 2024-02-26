package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.ChatRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>,
    ChatRoomRepositoryCustom {

    @Query("select c from ChatRoom as c join fetch UserChatRoom as ucr on c.id = ucr.chatRoom.id where ucr.user.id =:userId")
    List<ChatRoom> findAllByUserId(Long userId);

    Optional<ChatRoom> findByRouteId(Long routeId);
}
