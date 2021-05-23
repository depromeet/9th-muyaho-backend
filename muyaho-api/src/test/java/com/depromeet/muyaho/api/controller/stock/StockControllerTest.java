package com.depromeet.muyaho.api.controller.stock;

import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.controller.ControllerTest;
import com.depromeet.muyaho.api.service.stock.dto.request.RetrieveStocksRequest;
import com.depromeet.muyaho.api.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
public class StockControllerTest extends ControllerTest {

    private StockMockApiCaller stockMockApiCaller;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        stockMockApiCaller = new StockMockApiCaller(mockMvc, objectMapper);
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        stockRepository.deleteAll();
    }

    @DisplayName("GET /api/v1/stock/list 해외주식 조회 200 OK")
    @Test
    void 현재_거래중인_해외주식_종목들을_조회한다() throws Exception {
        // given
        Stock overseas = StockCreator.createActiveOverseas("AAPL", "Apple Inc");
        Stock domestic = StockCreator.createActiveDomestic("181710", "NHN");
        Stock bitcoin = StockCreator.createActiveBitCoin("KRW-BTC", "비트코인");
        stockRepository.saveAll(Arrays.asList(overseas, domestic, bitcoin));

        RetrieveStocksRequest request = RetrieveStocksRequest.testInstance(StockMarketType.OVERSEAS_STOCK);

        // when
        ApiResponse<List<StockInfoResponse>> response = stockMockApiCaller.retrieveStocks(request, 200);

        // then
        assertThat(response.getData()).hasSize(1);
        assertStockInfoResponse(response.getData().get(0), overseas.getId(), overseas.getCode(), overseas.getName(), overseas.getType());
    }

    @DisplayName("GET /api/v1/stock/list 비트코인 조회 200 OK")
    @Test
    void 현재_거래중인_비트코인_종목들을_조회한다() throws Exception {
        // given
        Stock overseas = StockCreator.createActiveOverseas("AAPL", "Apple Inc");
        Stock domestic = StockCreator.createActiveDomestic("181710", "NHN");
        Stock bitcoin = StockCreator.createActiveBitCoin("KRW-BTC", "비트코인");
        stockRepository.saveAll(Arrays.asList(overseas, domestic, bitcoin));

        RetrieveStocksRequest request = RetrieveStocksRequest.testInstance(StockMarketType.BITCOIN);

        // when
        ApiResponse<List<StockInfoResponse>> response = stockMockApiCaller.retrieveStocks(request, 200);

        // then
        assertThat(response.getData()).hasSize(1);
        assertStockInfoResponse(response.getData().get(0), bitcoin.getId(), bitcoin.getCode(), bitcoin.getName(), bitcoin.getType());
    }

    @DisplayName("GET /api/v1/stock/list 국내주식 조회 200 OK")
    @Test
    void 현재_거래중인_국내주식_종목들을_조회한다() throws Exception {
        // given
        Stock overseas = StockCreator.createActiveOverseas("AAPL", "Apple Inc");
        Stock domestic = StockCreator.createActiveDomestic("181710", "NHN");
        Stock bitcoin = StockCreator.createActiveBitCoin("KRW-BTC", "비트코인");
        stockRepository.saveAll(Arrays.asList(overseas, domestic, bitcoin));

        RetrieveStocksRequest request = RetrieveStocksRequest.testInstance(StockMarketType.DOMESTIC_STOCK);

        // when
        ApiResponse<List<StockInfoResponse>> response = stockMockApiCaller.retrieveStocks(request, 200);

        // then
        assertThat(response.getData()).hasSize(1);
        assertStockInfoResponse(response.getData().get(0), domestic.getId(), domestic.getCode(), domestic.getName(), domestic.getType());
    }

    private void assertStockInfoResponse(StockInfoResponse response, Long stockId, String code, String name, StockMarketType type) {
        assertThat(response.getId()).isEqualTo(stockId);
        assertThat(response.getCode()).isEqualTo(code);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getType()).isEqualTo(type);
    }

}
