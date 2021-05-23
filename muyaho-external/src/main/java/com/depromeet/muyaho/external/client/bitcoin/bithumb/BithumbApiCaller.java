package com.depromeet.muyaho.external.client.bitcoin.bithumb;

import com.depromeet.muyaho.external.client.bitcoin.bithumb.dto.response.BithumbTradeInfoResponse;

public interface BithumbApiCaller {

    BithumbTradeInfoResponse fetchCurrentPrice(String codes);

}
