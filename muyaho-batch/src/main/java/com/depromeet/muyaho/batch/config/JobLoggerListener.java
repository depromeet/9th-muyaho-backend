package com.depromeet.muyaho.batch.config;

import com.depromeet.muyaho.common.utils.LocalDateTimeUtils;
import com.depromeet.muyaho.domain.event.notification.ApplicationEventOccurredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobLoggerListener implements JobExecutionListener {

    private static final String START_MESSAGE = "%s is beginning execution";
    private static final String END_MESSAGE = "%s has completed with the status %s";

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        eventPublisher.publishEvent(ApplicationEventOccurredEvent.of(String.format(START_MESSAGE, jobExecution.getJobInstance().getJobName()), LocalDateTimeUtils.now()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        eventPublisher.publishEvent(ApplicationEventOccurredEvent.of(
            String.format(END_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()), LocalDateTimeUtils.now()));
    }

}
