package com.lisade.togeduck.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteRegistrationRequest {

    private Long stationId;
    private Long busId;
    private Integer distance;
    private Integer expectedTime;
}
