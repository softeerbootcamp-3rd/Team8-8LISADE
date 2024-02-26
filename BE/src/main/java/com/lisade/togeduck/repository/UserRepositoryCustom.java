package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.UserTicketResponse;

public interface UserRepositoryCustom {

    UserTicketResponse getTicket(Long userId, Long routeId);
}
