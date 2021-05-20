package com.depromeet.muyaho.api.service.stock;

import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.domain.domain.stock.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void cleanUp() {
        stockRepository.deleteAll();
    }

    @MethodSource("각각_타입의_주식_리스트")
    @ParameterizedTest
    void 등록된_종목_리스트를_불러온다(String code, String name, StockMarketType type) {
        // given
        stockRepository.save(StockCreator.createActive(code, name, type));

        // when
        List<StockInfoResponse> responseList = stockService.retrieveStockInfo(type);

        // then
        assertThat(responseList).hasSize(1);
        assertStockResponse(responseList.get(0), code, name);
    }

    @Test
    void 해당_타입의_등록된_종목_리스트만_불러온다() {
        // given
        Stock stock1 = StockCreator.createActive("code1", "국내주식", StockMarketType.DOMESTIC_STOCK);
        Stock stock2 = StockCreator.createActive("code2", "해외주식", StockMarketType.OVERSEAS_STOCK);
        stockRepository.saveAll(Arrays.asList(stock1, stock2));

        // when
        List<StockInfoResponse> responseList = stockService.retrieveStockInfo(StockMarketType.BITCOIN);

        // then
        assertThat(responseList).isEmpty();
    }

    @Test
    void 따로_통화_제한이_없는경우_활성화된_모든_주식을_조회한다() {
        // given
        Stock stock1 = StockCreator.createActive("KRW-ABC", "제한 없는 국내주식", StockMarketType.DOMESTIC_STOCK);
        Stock stock2 = StockCreator.createActive("ABC", "제한 없는 국내주식", StockMarketType.DOMESTIC_STOCK);
        stockRepository.saveAll(Arrays.asList(stock1, stock2));

        // when
        List<StockInfoResponse> responseList = stockService.retrieveStockInfo(StockMarketType.DOMESTIC_STOCK);

        // then
        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).getCode()).isEqualTo(stock2.getCode());
        assertThat(responseList.get(1).getCode()).isEqualTo(stock1.getCode());
    }

    private static Stream<Arguments> 각각_타입의_주식_리스트() {
        return Stream.of(
            Arguments.of("KRW-BTC", "비트코인", StockMarketType.BITCOIN),
            Arguments.of("oversea", "해외주식", StockMarketType.OVERSEAS_STOCK),
            Arguments.of("domestic", "국내주식", StockMarketType.DOMESTIC_STOCK)
        );
    }

    private void assertStockResponse(StockInfoResponse stockInfoResponse, String code, String name) {
        assertThat(stockInfoResponse.getCode()).isEqualTo(code);
        assertThat(stockInfoResponse.getName()).isEqualTo(name);
    }

}
