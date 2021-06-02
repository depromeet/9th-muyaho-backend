package com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpBitCodesResponse {

    private String market;

    private String koreanName;

    private String englishName;

    private UpBitCodesResponse(String market, String koreanName, String englishName) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
    }

    public static UpBitCodesResponse testInstance(String market, String name) {
        return new UpBitCodesResponse(market, name, "영어 이름");
    }

}
