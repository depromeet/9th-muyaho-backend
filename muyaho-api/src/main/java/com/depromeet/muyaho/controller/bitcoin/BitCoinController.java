package com.depromeet.muyaho.controller.bitcoin;

import com.depromeet.muyaho.service.stock.bitcoin.dto.request.RetrieveUpBitTradeInfoRequest;
import com.depromeet.muyaho.service.stock.dto.response.TradeInfoResponse;
import com.depromeet.muyaho.controller.ApiResponse;
import com.depromeet.muyaho.service.stock.bitcoin.BitCoinService;
import com.depromeet.muyaho.service.stock.dto.response.MarketInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BitCoinController {

    private final BitCoinService bitCoinService;

    @Operation(summary = "비트코인 전체 종목코드/종목명을 조회하는 API")
    @GetMapping("/api/v1/tickets/bitcoin")
    public ApiResponse<List<MarketInfoResponse>> retrieveBitCoinMarkets() {
        return ApiResponse.success(bitCoinService.retrieveUpBitMarkets());
    }

    @Deprecated
    @Operation(summary = "(테스트 확인용) 업비트 종목코드들에 대한 현재가를 조회하는 API")
    @GetMapping("/api/v1/trade/bitcoin/upbit")
    public ApiResponse<List<TradeInfoResponse>> retrieveUpBitTradeInfo(@Valid RetrieveUpBitTradeInfoRequest request) {
        return ApiResponse.success(bitCoinService.retrieveUpBitTrade(request.getMarketCodes()));
    }

    @Deprecated
    @Operation(summary = "(테스트 확인용) 빗썸 종목코드들에 대한 현재가를 조회하는 API")
    @GetMapping("/api/v1/trade/bitcoin/bithumb")
    public ApiResponse<List<TradeInfoResponse>> retrieveBithumbTradeInfo(@Valid RetrieveUpBitTradeInfoRequest request) {
        return ApiResponse.success(bitCoinService.retrieveBithumbTrade(request.getMarketCodes()));
    }

}
