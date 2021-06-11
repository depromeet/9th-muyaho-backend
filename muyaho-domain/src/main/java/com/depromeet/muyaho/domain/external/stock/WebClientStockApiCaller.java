package com.depromeet.muyaho.domain.external.stock;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.external.stock.dto.component.StockCodesComponent;
import com.depromeet.muyaho.domain.external.stock.dto.component.StockPriceComponent;
import com.depromeet.muyaho.domain.external.stock.dto.response.StockCodesResponse;
import com.depromeet.muyaho.domain.external.stock.dto.response.StockPriceResponse;
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
public class WebClientStockApiCaller implements StockApiCaller {

    private final StockCodesComponent stockCodesComponent;
    private final StockPriceComponent stockPriceComponent;
    private final WebClient webClient;

    public List<StockCodesResponse> fetchListedStocksCodes(StockType type) {
        log.info("주식 종목 코드/명 요청 API 사용 주식 종류: {}", type);

        return webClient.get()
            .uri(String.format("%s?type=%s", stockCodesComponent.getUrl(), type.getType()))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, errResponse -> Mono.error(new ValidationException(String.format("주식 종목 코드 조회 외부 API:  잘못된 주식 타입 (%s) 입니다", type))))
            .onStatus(HttpStatus::is5xxServerError, errorResponse -> Mono.error(new BadGatewayException(String.format("주식 종목 코드 조회 외부 API 연동 중 에러가 발생하였습니다. 요청: (%s)", type))))
            .bodyToMono(new ParameterizedTypeReference<List<StockCodesResponse>>() {
            })
            .block();
    }

    public List<StockPriceResponse> fetchCurrentStockPrice(String codes) {
        log.info("주식 햔재가 요청 API 사용 종목 코드: {}", codes);

        return webClient.get()
            .uri(String.format("%s?codes=%s", stockPriceComponent.getUrl(), codes))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, errResponse -> Mono.error(new ValidationException(String.format("주식 현재가 조회 외부 API:  잘못된 주식 코드 (%s) 입니다", codes))))
            .onStatus(HttpStatus::is5xxServerError, errResponse -> Mono.error(new BadGatewayException(String.format("주식 현재가 조회 외부 API 연동 중 에러가 발생하였습니다 요청: (%s)", codes))))
            .bodyToMono(new ParameterizedTypeReference<List<StockPriceResponse>>() {
            })
            .block();
    }

}
