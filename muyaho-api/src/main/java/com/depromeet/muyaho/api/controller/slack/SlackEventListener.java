package com.depromeet.muyaho.api.controller.slack;

import com.depromeet.muyaho.domain.event.slack.ApplicationEventOccurredEvent;
import com.depromeet.muyaho.domain.event.slack.ServerErrorOccurredEvent;
import com.depromeet.muyaho.domain.external.slack.SlackApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SlackEventListener {

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
