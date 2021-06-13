package com.depromeet.muyaho.external.client.slack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"local", "local-will"})
@Component
public class LocalWebClientApiCallerImpl implements SlackApiCaller {

    @Override
    public void postMessage(String message) {
        log.info(message);
    }

}
