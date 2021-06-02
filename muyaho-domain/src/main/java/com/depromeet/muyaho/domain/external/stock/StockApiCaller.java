package com.depromeet.muyaho.domain.external.stock;

import com.depromeet.muyaho.domain.external.stock.dto.response.StockCodesResponse;
import com.depromeet.muyaho.domain.external.stock.dto.response.StockPriceResponse;

import java.util.List;

public interface StockApiCaller {

    List<StockCodesResponse> fetchListedStocksCodes(StockType type);

    List<StockPriceResponse> fetchCurrentStockPrice(String codes);

}
