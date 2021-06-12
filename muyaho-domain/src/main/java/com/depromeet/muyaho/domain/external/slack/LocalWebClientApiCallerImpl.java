package com.depromeet.muyaho.domain.external.slack;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class LocalWebClientApiCallerImpl implements SlackApiCaller {

    @Override
    public void postMessage(String message) {
    }

}
