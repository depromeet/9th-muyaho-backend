package com.depromeet.muyaho.service.stock.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInfoRequest {

    private String code;

    private String name;

    public static StockInfoRequest of(String code, String name) {
        return new StockInfoRequest(code, name);
    }

}
