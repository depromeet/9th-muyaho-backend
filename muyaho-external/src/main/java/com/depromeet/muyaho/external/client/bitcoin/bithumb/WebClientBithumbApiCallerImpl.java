package com.depromeet.muyaho.external.client.bitcoin.bithumb;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.component.BithumbTradeComponent;
import com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.response.BithumbTradeInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebClientBithumbApiCallerImpl implements BithumbApiCaller {

    private final BithumbTradeComponent bithumbTradeComponent;
    private final WebClient webClient;

    @Override
    public BithumbTradeInfoResponse retrieveTrades(String marketCode) {
        log.info("빗썸 가격정보 조회 요청 API 사용 marketCode: {}", marketCode);
        return webClient.get()
            .uri(bithumbTradeComponent.getUrl().concat("/").concat(marketCode))
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("빗썸 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(BithumbTradeInfoResponse.class)
            .block();
    }

}
