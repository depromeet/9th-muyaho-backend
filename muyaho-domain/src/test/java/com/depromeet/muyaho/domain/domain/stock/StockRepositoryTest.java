package com.depromeet.muyaho.domain.domain.stock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void cleanUp() {
        stockRepository.deleteAll();
    }

    @Test
    void 활성화된_주식만_조회된다() {
        // given
        StockMarketType type = StockMarketType.BITCOIN;
        Stock allowedStock = StockCreator.createActive("AABC", "허용된 비트코인", type);
        Stock notAllowedStock = StockCreator.createDisable("ABC", "허용되지 않은 비트코인", type);
        stockRepository.saveAll(Arrays.asList(allowedStock, notAllowedStock));

        // when
        List<Stock> stockList = stockRepository.findAllActiveStockByType(type);

        // then
        assertThat(stockList).hasSize(1);
        assertThat(stockList.get(0).getCode()).isEqualTo(allowedStock.getCode());
    }

}
