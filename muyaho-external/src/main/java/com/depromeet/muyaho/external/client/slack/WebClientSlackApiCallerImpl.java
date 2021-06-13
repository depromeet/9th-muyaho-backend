package com.depromeet.muyaho.external.client.slack;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.external.client.slack.dto.component.SlackApiCallerComponent;
import com.depromeet.muyaho.external.client.slack.dto.request.PostSlackMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Profile({"dev", "prod"})
@RequiredArgsConstructor
@Component
public class WebClientSlackApiCallerImpl implements SlackApiCaller {

    private final WebClient webClient;
    private final SlackApiCallerComponent slackApiCallerComponent;

    @Override
    public void postMessage(String message) {
        webClient.post()
            .uri(slackApiCallerComponent.getUrl())
            .body(Mono.just(PostSlackMessageRequest.of(message)), PostSlackMessageRequest.class)
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("슬랙 API 연동중 에러가 발생하였습니다.")))
            .bodyToMono(String.class)
            .block();
    }

}
