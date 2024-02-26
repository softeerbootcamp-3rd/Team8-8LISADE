package com.lisade.togeduck.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmType {
    MESSAGE("MESSAGE"),
    JOIN("JOIN"),
    ;
    
    private final String type;
}
