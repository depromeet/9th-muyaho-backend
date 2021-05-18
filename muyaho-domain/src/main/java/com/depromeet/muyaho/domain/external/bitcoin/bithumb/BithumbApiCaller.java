package com.depromeet.muyaho.domain.external.bitcoin.bithumb;

import com.depromeet.muyaho.domain.external.bitcoin.bithumb.dto.response.BithumbTradeInfoResponse;

public interface BithumbApiCaller {

    BithumbTradeInfoResponse retrieveTrades(String marketCode);

}
