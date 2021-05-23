package com.depromeet.muyaho.external.client.bitcoin.bithumb;

import com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.response.BithumbTradeInfoResponse;

public interface BitHumbApiCaller {

    BithumbTradeInfoResponse fetchCurrentPrice(String marketCode);

}
