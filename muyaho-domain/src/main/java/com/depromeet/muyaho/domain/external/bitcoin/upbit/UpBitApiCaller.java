package com.depromeet.muyaho.domain.external.bitcoin.upbit;

import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.domain.external.bitcoin.upbit.dto.response.UpBitTradeInfoResponse;

import java.util.List;

public interface UpBitApiCaller {

    List<UpBitMarketResponse> retrieveMarkets();

    List<UpBitTradeInfoResponse> retrieveTrades(String marketCode);

}
