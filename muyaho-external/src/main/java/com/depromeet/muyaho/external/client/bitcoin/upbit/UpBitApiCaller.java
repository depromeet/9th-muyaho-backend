package com.depromeet.muyaho.external.client.bitcoin.upbit;

import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitCodesResponse;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitPriceResponse;

import java.util.List;

public interface UpBitApiCaller {

    List<UpBitCodesResponse> fetchListedBitcoins();

    List<UpBitPriceResponse> fetchCurrentBitcoinPrice(String codes);

}
