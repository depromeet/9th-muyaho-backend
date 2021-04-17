package com.depromeet.muyaho.service.stock.bitcoin;

import com.depromeet.muyaho.external.bithumb.BithumbApiCaller;
import com.depromeet.muyaho.external.bithumb.dto.response.BithumbTradeInfoResponse;
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

    private static final int UP_BIT_TRADE_PRICE = 100000;
    private static final int UP_BIT_HIGH_PRICE = 200000;
    private static final int UP_BIT_LOW_PRICE = 50000;

    private static final int BITHUMB_TRADE_PRICE = 700000;
    private static final int BITHUMB_HIGH_PRICE = 500000;
    private static final int BITHUMB_LOW_PRICE = 30000;

    @BeforeEach
    void setUp() {
        bitCoinService = new BitCoinService(new StubUpBitApiCaller(), new StubBithubApiCaller());
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
    void 업비트_조회시_비트코인_코드를_입력하면_현재_주가_정보가_반환된다() {
        // when
        List<TradeInfoResponse> tradeInfoResponses = bitCoinService.retrieveUpBitTrade("ABC");

        // then
        assertThat(tradeInfoResponses).hasSize(1);
        assertThat(tradeInfoResponses.get(0).getMarket()).isEqualTo("ABC");
        assertThat(tradeInfoResponses.get(0).getTradePrice()).isEqualTo(UP_BIT_TRADE_PRICE);
        assertThat(tradeInfoResponses.get(0).getHighPrice()).isEqualTo(UP_BIT_HIGH_PRICE);
        assertThat(tradeInfoResponses.get(0).getLowPrice()).isEqualTo(UP_BIT_LOW_PRICE);
    }

    @Test
    void 빗썸_조회시_비트코인_코드를_입력하면_현재_주가_정보가_반환된다() {
        // when
        List<TradeInfoResponse> tradeInfoResponses = bitCoinService.retrieveBithumbTrade("ABC");

        // then
        assertThat(tradeInfoResponses).hasSize(1);
        assertThat(tradeInfoResponses.get(0).getMarket()).isEqualTo("ABC");
        assertThat(tradeInfoResponses.get(0).getTradePrice()).isEqualTo(BITHUMB_TRADE_PRICE);
        assertThat(tradeInfoResponses.get(0).getHighPrice()).isEqualTo(BITHUMB_HIGH_PRICE);
        assertThat(tradeInfoResponses.get(0).getLowPrice()).isEqualTo(BITHUMB_LOW_PRICE);
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
                .tradePrice(UP_BIT_TRADE_PRICE)
                .highPrice(UP_BIT_HIGH_PRICE)
                .lowPrice(UP_BIT_LOW_PRICE)
                .build());
        }
    }

    private static class StubBithubApiCaller implements BithumbApiCaller {
        @Override
        public BithumbTradeInfoResponse retrieveTrades(String marketCode) {
            return BithumbTradeInfoResponse.testBuilder()
                .closingPrice(BITHUMB_TRADE_PRICE)
                .maxPrice(BITHUMB_HIGH_PRICE)
                .minPrice(BITHUMB_LOW_PRICE)
                .build();
        }
    }

}
