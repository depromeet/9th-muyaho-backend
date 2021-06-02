package com.depromeet.muyaho.domain.external.bitcoin.upbit;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.component.UpBitCodesComponent;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.component.UpBitPriceComponent;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response.UpBitCodesResponse;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response.UpBitPriceResponse;
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
public class WebClientUpBitApiCaller implements UpBitApiCaller {

    private final WebClient webClient;
    private final UpBitCodesComponent upBitCodesComponent;
    private final UpBitPriceComponent upBitPriceComponent;

    @Override
    public List<UpBitCodesResponse> fetchListedBitcoins() {
        log.info("업비트에서 현재 거래되는 종목들을 조회합니다..");

        return webClient.get()
            .uri(upBitCodesComponent.getUrl())
            .retrieve()
            .onStatus(HttpStatus::isError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitCodesResponse>>() {
            })
            .block();
    }

    @Override
    public List<UpBitPriceResponse> fetchCurrentBitcoinPrice(String codes) {
        log.info("업비트에서 현재가를 조회힙니다 codes: {}", codes);

        return webClient.get()
            .uri(upBitPriceComponent.getUrl(), uriBuilder -> uriBuilder.queryParam("markets", codes).build())
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, errorResponse -> Mono.error(new ValidationException(String.format("잘못된 종목 코드 (%s) 입니다", codes))))
            .onStatus(HttpStatus::is5xxServerError, errorResponse -> Mono.error(new BadGatewayException("업비트 외부 API 연동 중 에러가 발생하였습니다")))
            .bodyToMono(new ParameterizedTypeReference<List<UpBitPriceResponse>>() {
            })
            .block();
    }

}
