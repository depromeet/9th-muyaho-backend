package com.depromeet.muyaho.api;

import com.depromeet.muyaho.common.utils.LocalDateTimeUtils;
import com.depromeet.muyaho.domain.external.slack.SlackApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationRunner implements CommandLineRunner, ApplicationListener<ContextClosedEvent> {

    private final SlackApiCaller slackApiCaller;

    @Override
    public void run(String... args) {
        slackApiCaller.postMessage(String.format("%s\ntime: (%s)", "API 서버가 시작됩니다", LocalDateTimeUtils.now()));
    }

    // 단, SIGKILL (강제 종료)시 이 이벤트가 호출되지 않음.
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        slackApiCaller.postMessage(String.format("%s\ntime: (%s)", "API 서버가 종료됩니다", LocalDateTimeUtils.now()));
    }

}
