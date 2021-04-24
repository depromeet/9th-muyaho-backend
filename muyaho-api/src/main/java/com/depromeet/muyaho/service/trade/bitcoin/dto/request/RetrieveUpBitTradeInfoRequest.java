package com.depromeet.muyaho.service.trade.bitcoin.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrieveUpBitTradeInfoRequest {

    @NotBlank
    private String marketCodes;

}
