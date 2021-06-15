package com.depromeet.muyaho.external.client.auth.kakao;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.external.client.auth.kakao.dto.component.KaKaoProfileComponent;
import com.depromeet.muyaho.external.client.auth.kakao.dto.component.KaKaoSignOutComponent;
import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoProfileResponse;
import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoSignOutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientKaKaoCaller implements KaKaoApiCaller {

    private final WebClient webClient;
    private final KaKaoProfileComponent kaKaoProfileComponent;
    private final KaKaoSignOutComponent kaKaoSignOutComponent;

    @Override
    public KaKaoProfileResponse getProfileInfo(String accessToken) {
        return webClient.get()
            .uri(kaKaoProfileComponent.getUrl())
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ValidationException(String.format("잘못된 액세스 토큰 (%s) 입니다", accessToken))))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BadGatewayException("카카오 로그인 연동 중 에러가 발생하였습니다")))
            .bodyToMono(KaKaoProfileResponse.class)
            .block();
    }

    @Override
    public KaKaoSignOutResponse signOut(String memberUid) {
        return webClient.post()
            .uri(kaKaoSignOutComponent.getUrl())
            .headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, kaKaoSignOutComponent.getAuthorizationValue()))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData(createRequestBody(memberUid)))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new ValidationException("잘못된 key 입니다")))
            .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BadGatewayException("카카오 탈퇴 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(KaKaoSignOutResponse.class)
            .block();
    }

    private MultiValueMap<String, String> createRequestBody(String targetId) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("target_id_type", "user_id");
        formData.add("target_id", targetId);
        return formData;
    }

}
