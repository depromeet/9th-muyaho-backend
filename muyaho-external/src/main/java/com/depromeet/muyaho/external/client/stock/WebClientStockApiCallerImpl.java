package com.depromeet.muyaho.external.client.stock;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.external.client.stock.dto.component.StockCodeComponent;
import com.depromeet.muyaho.external.client.stock.dto.component.StockPriceComponent;
import com.depromeet.muyaho.external.client.stock.dto.response.StockCodeResponse;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;
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
public class WebClientStockApiCallerImpl implements StockApiCaller {

    private final StockCodeComponent stockCodeComponent;
    private final StockPriceComponent stockPriceComponent;
    private final WebClient webClient;

    public List<StockCodeResponse> getStockCodes(StockType type) {
        log.info("{} 주식 종목 코드/명 요청 API 사용", type);
        return webClient.get()
            .uri(String.format("%s?type=%s", stockCodeComponent.getUrl(), type.getType()))
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException(String.format("주식 (%s) 외부 API 연동 중 에러가 발생하였습니다", type))))
            .bodyToMono(new ParameterizedTypeReference<List<StockCodeResponse>>() {
            })
            .block();
    }

    public List<StockPriceResponse> getStockPrice(String codes) {
        log.info("{} 주식 햔재가 요청 API 사용", codes);
        return webClient.get()
            .uri(String.format("%s?codes=%s", stockPriceComponent.getUrl(), codes))
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("주식 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<StockPriceResponse>>() {
            })
            .block();
    }

}
