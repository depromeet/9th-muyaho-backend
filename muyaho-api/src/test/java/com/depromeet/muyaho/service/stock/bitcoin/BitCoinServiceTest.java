package com.depromeet.muyaho.service.stock.bitcoin;

import com.depromeet.muyaho.service.stock.dto.response.MarketInfoResponse;
import com.depromeet.muyaho.service.stock.dto.response.TradeInfoResponse;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BitCoinServiceTest {

    private BitCoinService bitCoinService;

    @BeforeEach
    void setUp() {
        bitCoinService = new BitCoinService(new StubUpBitApiCaller());
    }

    @Test
    void 비트코인_코인_리스트를_조회한다() {
        // when
        List<MarketInfoResponse> responses = bitCoinService.retrieveUpBitMarkets();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getCode()).isEqualTo("ABC");
        assertThat(responses.get(0).getName()).isEqualTo("비트코인1");

        assertThat(responses.get(1).getCode()).isEqualTo("BCD");
        assertThat(responses.get(1).getName()).isEqualTo("비트코인2");
    }

    @Test
    void 비트코인_코드를_입력하면_현재_주가_정보가_반환된다() {
        // when
        List<TradeInfoResponse> tradeInfoResponses = bitCoinService.retrieveUpBitTrade("ABC");

        // then
        assertThat(tradeInfoResponses).hasSize(1);
        assertThat(tradeInfoResponses.get(0).getMarket()).isEqualTo("ABC");
        assertThat(tradeInfoResponses.get(0).getTradePrice()).isEqualTo(10000);
    }

    private static class StubUpBitApiCaller implements UpBitApiCaller {
        @Override
        public List<UpBitMarketResponse> retrieveMarkets() {
            return Arrays.asList(UpBitMarketResponse.testInstance("KRW-ABC", "비트코인1"), UpBitMarketResponse.testInstance("KRW-BCD", "비트코인2"));
        }

        @Override
        public List<UpBitTradeInfoResponse> retrieveTrades(String marketCode) {
            return Collections.singletonList(UpBitTradeInfoResponse.testBuilder()
                .market(marketCode)
                .tradePrice(10000)
                .build());
        }
    }

}
