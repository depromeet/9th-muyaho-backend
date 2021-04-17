package com.depromeet.muyaho.external.bithumb;

import com.depromeet.muyaho.external.bithumb.dto.response.BithumbTradeInfoResponse;

public interface BithumbApiCaller {

    BithumbTradeInfoResponse retrieveTrades(String marketCode);

}
