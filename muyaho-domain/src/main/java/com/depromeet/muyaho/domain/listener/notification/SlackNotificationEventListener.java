package com.depromeet.muyaho.domain.listener.notification;

import com.depromeet.muyaho.domain.event.notification.ApplicationEventOccurredEvent;
import com.depromeet.muyaho.domain.event.notification.ServerErrorOccurredEvent;
import com.depromeet.muyaho.domain.external.slack.SlackApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SlackNotificationEventListener {

    private final SlackApiCaller slackApiCaller;

    @EventListener
    public void sendErrorNotification(ServerErrorOccurredEvent event) {
        slackApiCaller.postMessage(String.format("[%s] message: (%s)\nerror: (%s)\ndatetime: (%s)",
            event.getType(), event.getMessage(), event.getException().getMessage(), event.getDateTime()));
    }

    @EventListener
    public void sendInfoNotification(ApplicationEventOccurredEvent event) {
        slackApiCaller.postMessage(String.format("[%s] %s\ntime: (%s)", event.getType(), event.getMessage(), event.getDateTime()));
    }

}
