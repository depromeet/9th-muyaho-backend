package com.depromeet.muyaho.domain.external.currency;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.domain.external.currency.dto.component.ExchangeRateComponent;
import com.depromeet.muyaho.domain.external.currency.dto.response.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class WebClientExchangeRateApiCaller implements ExchangeRateApiCaller {

    private final WebClient webclient;
    private final ExchangeRateComponent exchangeRateComponent;

    @Override
    public BigDecimal fetchExchangeRate() {
        return fetchCurrentExchangeRate().getUSDToKRWExchangeRate();
    }

    private ExchangeRateResponse fetchCurrentExchangeRate() {
        return webclient.get()
            .uri(exchangeRateComponent.getUrl())
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<ExchangeRateResponse>() {
            })
            .block();
    }

}
