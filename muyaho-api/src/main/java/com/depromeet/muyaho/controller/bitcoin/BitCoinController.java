package com.depromeet.muyaho.controller.bitcoin;

import com.depromeet.muyaho.service.stock.bitcoin.dto.request.RetrieveUpBitTradeInfoRequest;
import com.depromeet.muyaho.service.stock.dto.response.TradeInfoResponse;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.stock.bitcoin.BitCoinService;
import com.depromeet.muyaho.service.stock.dto.response.MarketInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BitCoinController {

    private final BitCoinService bitCoinService;

    @GetMapping("/api/v1/tickets/bitcoin")
    public ApiResponse<List<MarketInfoResponse>> retrieveBitCoinMarkets() {
        return ApiResponse.success(bitCoinService.retrieveUpBitMarkets());
    }

    @GetMapping("/api/v1/trade/bitcoin/upbit")
    public ApiResponse<List<TradeInfoResponse>> retrieveUpBitTradeInfo(@Valid RetrieveUpBitTradeInfoRequest request) {
        return ApiResponse.success(bitCoinService.retrieveUpBitTrade(request.getMarketCodes()));
    }

}
