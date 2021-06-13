package com.depromeet.muyaho.domain.event.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationEventOccurredEvent {

    private final EventLevelType type;

    private final String message;

    private final LocalDateTime dateTime;

    public static ApplicationEventOccurredEvent of(String message, LocalDateTime dateTime) {
        return new ApplicationEventOccurredEvent(EventLevelType.INFO, message, dateTime);
    }

}
