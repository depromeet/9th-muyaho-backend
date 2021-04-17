package com.depromeet.muyaho.external.bithumb;

import com.depromeet.muyaho.external.bithumb.dto.component.BithumbTradeComponent;
import com.depromeet.muyaho.external.bithumb.dto.response.BithumbTradeInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientBithumbApiCallerImpl implements BithumbApiCaller {

    private final BithumbTradeComponent bithumbTradeComponent;
    private final WebClient webClient;

    @Override
    public BithumbTradeInfoResponse retrieveTrades(String marketCode) {
        return webClient.get()
            .uri(bithumbTradeComponent.getUrl().concat("/").concat(marketCode))
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new IllegalArgumentException("빗썸 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(BithumbTradeInfoResponse.class)
            .block();
    }

}
