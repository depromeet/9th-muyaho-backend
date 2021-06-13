package com.depromeet.muyaho.external.client.stock;

import com.depromeet.muyaho.external.client.stock.dto.response.StockCodesResponse;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;

import java.util.List;

public interface StockApiCaller {

    List<StockCodesResponse> fetchListedStocksCodes(StockType type);

    List<StockPriceResponse> fetchCurrentStockPrice(String codes);

}
