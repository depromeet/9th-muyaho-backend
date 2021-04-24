package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.*;
import com.depromeet.muyaho.service.stock.dto.request.StockInfoRequest;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
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
    void 주식종목_갱신시_새롭게_상장된_주식의_경우_활성화로_새롭게_저장된다(String code, String name, StockMarketType type) {
        // when
        stockService.renewStock(type, Collections.singletonList(StockInfoRequest.of(code, name)));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(code);
        assertThat(stockList.get(0).getName()).isEqualTo(name);
        assertThat(stockList.get(0).getType()).isEqualTo(type);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    @MethodSource("각각_타입의_주식_리스트")
    @ParameterizedTest
    void 주식종목_갱신시_기존에_상장중이였는데_상장폐지되는경우_비활성화로_변경된다(String code, String name, StockMarketType type) {
        // given
        stockRepository.save(StockCreator.createActive(code, name, type));

        // when
        stockService.renewStock(type, Collections.emptyList());

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(code);
        assertThat(stockList.get(0).getName()).isEqualTo(name);
        assertThat(stockList.get(0).getType()).isEqualTo(type);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.DISABLED);
    }

    @MethodSource("각각_타입의_주식_리스트")
    @ParameterizedTest
    void 주식종목_갱신시_기존에_비활성되었는데_다시_상장되는경우_활성화로_변경된다(String code, String name, StockMarketType type) {
        // given
        stockRepository.save(StockCreator.createDisable(code, name, type));

        // when
        stockService.renewStock(type, Collections.singletonList(StockInfoRequest.of(code, name)));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(code);
        assertThat(stockList.get(0).getName()).isEqualTo(name);
        assertThat(stockList.get(0).getType()).isEqualTo(type);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    @MethodSource("각각_타입의_주식_리스트")
    @ParameterizedTest
    void 주식종목을_갱신요청할때_기존에_있었는데_나오는경우_활성화로_유지된다() {
        // given
        String code = "code";
        String name = "새로운 주식";
        StockMarketType type = StockMarketType.BITCOIN;
        stockRepository.save(StockCreator.createActive(code, name, type));

        // when
        stockService.renewStock(type, Collections.singletonList(StockInfoRequest.of(code, name)));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(code);
        assertThat(stockList.get(0).getName()).isEqualTo(name);
        assertThat(stockList.get(0).getType()).isEqualTo(type);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    private static Stream<Arguments> 각각_타입의_주식_리스트() {
        return Stream.of(
            Arguments.of("bit", "비트코인", StockMarketType.BITCOIN),
            Arguments.of("oversea", "해외주식", StockMarketType.OVERSEAS_STOCK),
            Arguments.of("domestic", "국내주식", StockMarketType.DOMESTIC_STOCK)
        );
    }

    @MethodSource("비트코인과_해외주식_주식_리스트")
    @ParameterizedTest
    void 주식종목을_갱신할때_다른_종류의_주식은_영향을_받지_않는다(String code, String name, StockMarketType type) {
        // given
        stockRepository.save(StockCreator.createActive(code, name, type));

        // when
        stockService.renewStock(StockMarketType.DOMESTIC_STOCK, Collections.emptyList());

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(code);
        assertThat(stockList.get(0).getName()).isEqualTo(name);
        assertThat(stockList.get(0).getType()).isEqualTo(type);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    private static Stream<Arguments> 비트코인과_해외주식_주식_리스트() {
        return Stream.of(
            Arguments.of("code", "비트코인", StockMarketType.BITCOIN),
            Arguments.of("code", "해외주식", StockMarketType.OVERSEAS_STOCK)
        );
    }

    @Test
    void 등록된_종목_리스트를_불러온다() {
        // given
        Stock stock1 = StockCreator.createActive("code1", "비트코인", StockMarketType.BITCOIN);
        Stock stock2 = StockCreator.createActive("code2", "리플", StockMarketType.BITCOIN);

        stockRepository.saveAll(Arrays.asList(stock1, stock2));

        // when
        List<StockInfoResponse> responseList = stockService.retrieveStockInfo(StockMarketType.BITCOIN);

        // then
        assertThat(responseList).hasSize(2);
        assertStockResponse(responseList.get(0), stock1.getCode(), stock1.getName());
        assertStockResponse(responseList.get(1), stock2.getCode(), stock2.getName());
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

    private void assertStockResponse(StockInfoResponse stockInfoResponse, String code, String name) {
        assertThat(stockInfoResponse.getCode()).isEqualTo(code);
        assertThat(stockInfoResponse.getName()).isEqualTo(name);
    }

}
