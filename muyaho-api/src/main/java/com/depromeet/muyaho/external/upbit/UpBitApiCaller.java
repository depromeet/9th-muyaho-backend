package com.depromeet.muyaho.external.upbit;

import com.depromeet.muyaho.external.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;

import java.util.List;

public interface UpBitApiCaller {

    List<UpBitMarketResponse> retrieveMarkets();

    List<UpBitTradeInfoResponse> retrieveTrades(String marketCode);

}
