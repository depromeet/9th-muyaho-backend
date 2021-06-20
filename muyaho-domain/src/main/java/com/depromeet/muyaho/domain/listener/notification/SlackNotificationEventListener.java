package com.depromeet.muyaho.domain.listener.notification;

import com.depromeet.muyaho.domain.event.notification.ApplicationEventOccurredEvent;
import com.depromeet.muyaho.domain.event.notification.ServerErrorOccurredEvent;
import com.depromeet.muyaho.external.client.slack.SlackApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SlackNotificationEventListener {

    private static final String ERROR_MESSAGE_FORMAT = "[%s] message: (%s)\nerror: (%s)\ndatetime: (%s)";
    private static final String INFO_MESSAGE_FORMAT = "[%s] %s\ntime: (%s)";

    private final SlackApiCaller slackApiCaller;

    @EventListener
    public void sendErrorNotification(ServerErrorOccurredEvent event) {
        log.info(String.format(ERROR_MESSAGE_FORMAT, event.getType(), event.getMessage(), event.getException().getMessage(), event.getDateTime()));
        slackApiCaller.postMessage(String.format(ERROR_MESSAGE_FORMAT,
            event.getType(), event.getMessage(), event.getException().getMessage(), event.getDateTime()));
    }

    @EventListener
    public void sendInfoNotification(ApplicationEventOccurredEvent event) {
        log.info((String.format(INFO_MESSAGE_FORMAT, event.getType(), event.getMessage(), event.getDateTime())));
        slackApiCaller.postMessage(String.format(INFO_MESSAGE_FORMAT, event.getType(), event.getMessage(), event.getDateTime()));
    }

}
