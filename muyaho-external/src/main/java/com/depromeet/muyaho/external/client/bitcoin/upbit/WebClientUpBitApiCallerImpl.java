package com.depromeet.muyaho.external.client.bitcoin.upbit;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.component.UpBitMarketsComponent;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.component.UpBitTradeComponent;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitTradeInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebClientUpBitApiCallerImpl implements UpBitApiCaller {

    private final WebClient webClient;
    private final UpBitMarketsComponent marketsComponent;
    private final UpBitTradeComponent tickerComponent;

    @Override
    public List<UpBitMarketResponse> retrieveMarkets() {
        log.info("업비트 현재 거래되는 종목명/코드 조회 요청 API 사용");
        return webClient.get()
            .uri(marketsComponent.getUrl())
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitMarketResponse>>() {
            })
            .block();
    }

    @Override
    public List<UpBitTradeInfoResponse> retrieveTrades(String marketCode) {
        log.info("업비트 가격정보 조회 요청 API 사용 marketCode: {}", marketCode);
        return webClient.get()
            .uri(tickerComponent.getUrl(), uriBuilder -> uriBuilder.queryParam("markets", marketCode).build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, errorResponse -> Mono.error(new ValidationException(String.format("잘못된 종목 코드 (%s) 입니다", marketCode))))
            .onStatus(HttpStatus::is5xxServerError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitTradeInfoResponse>>() {
            })
            .block();
    }

}
