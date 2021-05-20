package com.depromeet.muyaho.external.client.currency;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.external.client.currency.dto.component.CurrencyRateComponent;
import com.depromeet.muyaho.external.client.currency.dto.response.CurrencyRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class WebClientCurrencyRateApiCallerImpl implements CurrencyRateApiCaller {

    private final WebClient webclient;
    private final CurrencyRateComponent currencyRateComponent;

    @Override
    public CurrencyRateResponse getCurrencyRate() {
        return webclient.get()
            .uri(currencyRateComponent.getUrl())
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<CurrencyRateResponse>() {
            })
            .block();
    }

}
