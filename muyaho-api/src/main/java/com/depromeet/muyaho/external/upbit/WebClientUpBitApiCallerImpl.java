package com.depromeet.muyaho.external.upbit;

import com.depromeet.muyaho.external.upbit.dto.component.UpBitMarketsComponent;
import com.depromeet.muyaho.external.upbit.dto.component.UpBitTickerComponent;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WebClientUpBitApiCallerImpl implements UpBitApiCaller {

    private final WebClient webClient;
    private final UpBitMarketsComponent marketsComponent;
    private final UpBitTickerComponent tickerComponent;

    @Override
    public List<UpBitMarketResponse> retrieveMarkets() {
        return webClient.get()
            .uri(marketsComponent.getUrl())
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new IllegalArgumentException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitMarketResponse>>() {
            })
            .block();
    }

    @Override
    public List<UpBitTradeInfoResponse> retrieveTrades(String marketCode) {
        return webClient.get()
            .uri(tickerComponent.getUrl(), uriBuilder -> uriBuilder.queryParam("markets", marketCode).build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, errorResponse -> Mono.error(new IllegalArgumentException(String.format("잘못된 종목 코드 (%s) 입니다", marketCode))))
            .onStatus(HttpStatus::is5xxServerError, errorResponse -> Mono.error(new IllegalArgumentException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitTradeInfoResponse>>() {
            })
            .block();
    }

}
