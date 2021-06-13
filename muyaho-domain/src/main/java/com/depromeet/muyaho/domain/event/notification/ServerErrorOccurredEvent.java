package com.depromeet.muyaho.domain.event.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerErrorOccurredEvent {

    private final EventLevelType type;

    private final String message;

    private final Exception exception;

    private final LocalDateTime dateTime;

    public static ServerErrorOccurredEvent of(String message, Exception exception, LocalDateTime dateTime) {
        return new ServerErrorOccurredEvent(EventLevelType.ERROR, message, exception, dateTime);
    }

}
