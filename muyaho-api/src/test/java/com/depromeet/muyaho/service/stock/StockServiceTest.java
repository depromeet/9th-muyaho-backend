package com.depromeet.muyaho.service.stock;

import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.stock.StockType;
import com.depromeet.muyaho.service.MemberSetUpTest;
import com.depromeet.muyaho.service.stock.dto.request.AddStockRequest;
import com.depromeet.muyaho.service.stock.dto.response.StockInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class StockServiceTest extends MemberSetUpTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @AfterEach
    void cleanUp() {
        stockRepository.deleteAll();
    }

    @Test
    void 내가_소유한_주식을_추가한다() {
        // given
        String stockCode = "비트코인";
        StockType type = StockType.BITCOIN;
        int purchasePrice = 10000;
        int quantity = 5;

        AddStockRequest request = AddStockRequest.testBuilder()
            .stockCode(stockCode)
            .type(type)
            .purchasePrice(purchasePrice)
            .quantity(quantity)
            .build();

        // when
        stockService.addStock(request, memberId);

        // then
        List<Stock> stockList = stockRepository.findAll();
        assertStock(stockList.get(0), memberId, stockCode, type, purchasePrice, quantity);
    }

    @Test
    void 회원이_이미_등록한_주식일경우_에러가_발생한다() {
        // given
        String stockCode = "비트코인";
        StockType type = StockType.BITCOIN;
        int purchasePrice = 10000;
        int quantity = 5;

        Stock stock = StockCreator.create(memberId, stockCode, type, purchasePrice, quantity);
        stockRepository.save(stock);

        AddStockRequest request = AddStockRequest.testBuilder()
            .stockCode(stockCode)
            .type(type)
            .purchasePrice(10000)
            .quantity(5)
            .build();

        // when & then
        assertThatThrownBy(() -> stockService.addStock(request, memberId)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 내가_소유한_주식리스트를_조회한다() {
        // given
        String stockCode = "비트코인";
        StockType type = StockType.BITCOIN;
        int purchasePrice = 10000;
        int quantity = 5;

        Stock stock = StockCreator.create(memberId, stockCode, type, purchasePrice, quantity);
        stockRepository.save(stock);

        // when
        List<StockInfoResponse> stockInfoResponses = stockService.retrieveMyStocks(memberId);

        // then
        assertStockInfoResponse(stockInfoResponses.get(0), stock.getId(), stockCode, type, purchasePrice, quantity);
    }

    private void assertStockInfoResponse(StockInfoResponse stockInfoResponse, Long stockId, String stockCode, StockType type, int purchasePrice, int quantity) {
        assertThat(stockInfoResponse.getStockId()).isEqualTo(stockId);
        assertThat(stockInfoResponse.getStockCode()).isEqualTo(stockCode);
        assertThat(stockInfoResponse.getType()).isEqualTo(type);
        assertThat(stockInfoResponse.getPurchasePrice()).isEqualTo(purchasePrice);
        assertThat(stockInfoResponse.getQuantity()).isEqualTo(quantity);
    }

    private void assertStock(Stock stock, Long memberId, String stockCode, StockType type, int purchasePrice, int quantity) {
        assertThat(stock.getMemberId()).isEqualTo(memberId);
        assertThat(stock.getStockCode()).isEqualTo(stockCode);
        assertThat(stock.getType()).isEqualTo(type);
        assertThat(stock.getPurchasePrice()).isEqualTo(purchasePrice);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
    }

}
