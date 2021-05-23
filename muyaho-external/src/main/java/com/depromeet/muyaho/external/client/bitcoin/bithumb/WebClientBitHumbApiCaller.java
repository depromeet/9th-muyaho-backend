package com.depromeet.muyaho.external.client.bitcoin.bithumb;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.component.BitHumbPriceComponent;
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
public class WebClientBitHumbApiCaller implements BithumbApiCaller {

    private final BitHumbPriceComponent bitHumbPriceComponent;
    private final WebClient webClient;

    @Override
    public BithumbTradeInfoResponse fetchCurrentPrice(String codes) {
        log.info("빗썸 가격정보 조회 요청 API 사용 marketCode: {}", codes);

        return webClient.get()
            .uri(bitHumbPriceComponent.getUrl().concat("/").concat(codes))
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("빗썸 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(BithumbTradeInfoResponse.class)
            .block();
    }

}
