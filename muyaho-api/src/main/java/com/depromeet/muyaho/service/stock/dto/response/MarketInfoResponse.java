package com.depromeet.muyaho.service.stock.dto.response;

import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketInfoResponse {

    private final String code;

    private final String name;

    public static MarketInfoResponse of(String code, String name) {
        return new MarketInfoResponse(code, name);
    }

}
