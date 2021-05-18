package com.depromeet.muyaho.domain.external.auth.kakao;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.external.auth.kakao.dto.component.KaKaoUserInfoComponent;
import com.depromeet.muyaho.domain.external.auth.kakao.dto.response.KaKaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientKaKaoCallerImpl implements KaKaoApiCaller {

    private final WebClient webClient;
    private final KaKaoUserInfoComponent kaKaoUserInfoComponent;

    @Override
    public KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken) {
        return webClient.get()
            .uri(kaKaoUserInfoComponent.getUrl())
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ValidationException(String.format("잘못된 액세스 토큰 (%s) 입니다", accessToken))))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BadGatewayException("카카오 로그인 연동 중 에러가 발생하였습니다")))
            .bodyToMono(KaKaoUserInfoResponse.class)
            .block();
    }

}
