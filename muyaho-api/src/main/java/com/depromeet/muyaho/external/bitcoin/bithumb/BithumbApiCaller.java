package com.depromeet.muyaho.external.bitcoin.bithumb;

import com.depromeet.muyaho.external.bitcoin.bithumb.dto.response.BithumbTradeInfoResponse;

public interface BithumbApiCaller {

    BithumbTradeInfoResponse retrieveTrades(String marketCode);

}
