package com.lisade.togeduck.entity.enums;

import java.util.Arrays;


public enum MessageAction {
    JOIN,
    MESSAGE,
    LEAVE;

    public static MessageAction getMessageAction(String action) {
        return Arrays.stream(MessageAction.values())
            .filter(messageAction -> messageAction.name().equals(action))
            .findFirst()
            .orElse(null);
    }
}
