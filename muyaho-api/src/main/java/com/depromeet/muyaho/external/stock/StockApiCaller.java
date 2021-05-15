package com.depromeet.muyaho.external.stock;

import com.depromeet.muyaho.external.stock.dto.response.StockCodeResponse;

import java.util.List;

public interface StockApiCaller {

    List<StockCodeResponse> getStockCodes(StockType type);
    
}
