package com.depromeet.muyaho.external.client.stock;

import com.depromeet.muyaho.external.client.stock.dto.response.StockCodeResponse;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;

import java.util.List;

public interface StockApiCaller {

    List<StockCodeResponse> getStockCodes(StockType type);

    List<StockPriceResponse> getStockPrice(String codes);

}
