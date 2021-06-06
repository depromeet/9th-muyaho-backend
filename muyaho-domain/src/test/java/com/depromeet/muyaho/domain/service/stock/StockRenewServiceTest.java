package com.depromeet.muyaho.domain.service.stock;

import com.depromeet.muyaho.domain.domain.stock.*;
import com.depromeet.muyaho.domain.service.stock.dto.request.StockInfoRequest;
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
public class StockRenewServiceTest {

    @Autowired
    private StockRenewService stockService;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void cleanUp() {
        stockRepository.deleteAll();
    }

    @Test
    void 종목_갱신시_해당_통화에서_허용된_코드만_등록된다() {
        // given
        String allowedCode = "KRW-ABC";
        String disAllowedCode = "ABC";

        // when
        stockService.renewStock(StockMarketType.BITCOIN, Arrays.asList(
            StockInfoRequest.of(allowedCode, "허용된 비트코인"),
            StockInfoRequest.of(disAllowedCode, "허용되지 않은 비트코인")));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(allowedCode);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    @Test
    void 따로_코드의_제한이_없는_주식의_경우_모두_등록된다() {
        // given
        String allowedCode1 = "Code1";
        String allowedCode2 = "StockCode1";

        // when
        stockService.renewStock(StockMarketType.DOMESTIC_STOCK, Arrays.asList(
            StockInfoRequest.of(allowedCode1, "제한 없는 국내 주식"),
            StockInfoRequest.of(allowedCode2, "제한 없는 국내 주식")));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(2);
        assertThat(stockList.get(0).getCode()).isEqualTo(allowedCode1);
        assertThat(stockList.get(0).getStatus()).isEqualTo(StockStatus.ACTIVE);

        assertThat(stockList.get(1).getCode()).isEqualTo(allowedCode2);
        assertThat(stockList.get(1).getStatus()).isEqualTo(StockStatus.ACTIVE);
    }

    @MethodSource("각각_타입의_주식_리스트")
    @ParameterizedTest
    void 주식종목_갱신시_새롭게_상장된_주식의_경우_활성화로_새롭게_저장된다(String code, String name, StockMarketType type) {
        // when
        stockService.renewStock(type, Collections.singletonList(StockInfoRequest.of(code, name)));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
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
        assertStock(stockList.get(0), code, name, type, StockStatus.DISABLED);
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
        assertStock(stockList.get(0), code, name, type, StockStatus.ACTIVE);
    }

    @Test
    void 주식종목을_갱신요청할때_기존에_있었는데_다시_나오는경우_활성화로_유지된다() {
        // given
        String code = "KRW-code";
        String name = "새로운 비트코인";
        StockMarketType type = StockMarketType.BITCOIN;
        stockRepository.save(StockCreator.createActive(code, name, type));

        // when
        stockService.renewStock(type, Collections.singletonList(StockInfoRequest.of(code, name)));

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(1);
        assertStock(stockList.get(0), code, name, type, StockStatus.ACTIVE);
    }

    private static Stream<Arguments> 각각_타입의_주식_리스트() {
        return Stream.of(
            Arguments.of("KRW-BTC", "비트코인", StockMarketType.BITCOIN),
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
        assertStock(stockList.get(0), code, name, type, StockStatus.ACTIVE);
    }

    private static Stream<Arguments> 비트코인과_해외주식_주식_리스트() {
        return Stream.of(
            Arguments.of("KRW-BTC", "비트코인", StockMarketType.BITCOIN),
            Arguments.of("code", "해외주식", StockMarketType.OVERSEAS_STOCK)
        );
    }

    private void assertStock(Stock stock, String code, String name, StockMarketType type, StockStatus status) {
        assertThat(stock.getCode()).isEqualTo(code);
        assertThat(stock.getName()).isEqualTo(name);
        assertThat(stock.getType()).isEqualTo(type);
        assertThat(stock.getStatus()).isEqualTo(status);
    }

}
