package com.depromeet.muyaho.domain.external.stock;

import com.depromeet.muyaho.domain.external.stock.dto.response.StockCodeResponse;
import com.depromeet.muyaho.domain.external.stock.dto.response.StockPriceResponse;

import java.util.List;

public interface StockApiCaller {

    List<StockCodeResponse> getStockCodes(StockType type);

    List<StockPriceResponse> getStockPrice(String codes);

}
