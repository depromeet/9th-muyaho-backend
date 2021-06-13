package com.depromeet.muyaho.api;

import com.depromeet.muyaho.common.utils.LocalDateTimeUtils;
import com.depromeet.muyaho.domain.event.notification.ApplicationEventOccurredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationRunner implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void run(String... args) {
        eventPublisher.publishEvent(ApplicationEventOccurredEvent.of("API 서버가 시작됩니다", LocalDateTimeUtils.now()));
    }

    // 단, SIGKILL시 이벤트가 호출되지 않음.
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        eventPublisher.publishEvent(ApplicationEventOccurredEvent.of("API 서버가 종료됩니다", LocalDateTimeUtils.now()));
    }

}
