package com.depromeet.muyaho.external.client.stock.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockCodeResponse {

    @JsonProperty("Symbol")
    private String code;

    @JsonProperty("Name")
    private String name;

}
