package com.depromeet.muyaho.domain.external.currency.dto.response;

import com.depromeet.muyaho.common.exception.BadGatewayException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRateResponse {

    @JsonProperty("update")
    private long timestamp;

    @JsonProperty("USDKRW")
    private List<BigDecimal> usdKrwRate;

    public BigDecimal getUSDToKRWExchangeRate() {
        if (usdKrwRate.size() <= 0) {
            throw new BadGatewayException("USD -> KRW로의 환율을 불러오는 도중 에러가 발생하였습니다.");
        }
        return usdKrwRate.get(0);
    }

}
