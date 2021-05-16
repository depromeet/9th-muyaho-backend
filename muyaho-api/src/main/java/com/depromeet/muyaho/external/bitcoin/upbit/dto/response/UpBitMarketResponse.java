package com.depromeet.muyaho.external.bitcoin.upbit.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpBitMarketResponse {

    private String market;

    private String koreanName;

    private String englishName;

    private UpBitMarketResponse(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }

    public static UpBitMarketResponse testInstance(String market, String name) {
        return new UpBitMarketResponse(market, name, "영어 이름");
    }

}
