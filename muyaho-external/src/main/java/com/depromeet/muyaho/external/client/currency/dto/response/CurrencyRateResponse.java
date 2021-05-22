package com.depromeet.muyaho.external.client.currency.dto.response;

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
public class CurrencyRateResponse {

    @JsonProperty("update")
    private long timestamp;

    @JsonProperty("USDKRW")
    private List<BigDecimal> usdKrwRate;

    @JsonProperty("KRWUSD")
    private List<BigDecimal> krwUsdRate;

    public BigDecimal getUSDToKRWExchangeRate() {
        if (usdKrwRate.size() <= 0) {
            throw new BadGatewayException("USD -> KRW로의 환율을 불러오는 도중 에러가 발생하였습니다.");
        }
        return usdKrwRate.get(0);
    }

    public BigDecimal getKRWToUSDExchangeRate() {
        if (krwUsdRate.size() <= 0) {
            throw new BadGatewayException("KRW -> USD로의 환율을 불러오는 도중 에러가 발생하였습니다.");
        }
        return krwUsdRate.get(0);
    }

}
