package com.depromeet.muyaho.domain.service.memberstock;

import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.domain.memberstock.*;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.service.MemberSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberStockRetrieveServiceTest extends MemberSetupTest {

    @Autowired
    private MemberStockRetrieveService memberStockRetrieveService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void cleanUp() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    void 다른_사람이_소유한_주식에_접근할_수없다() {
        // given
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        memberStockRepository.save(MemberStockCreator.create(999L, stock, new BigDecimal(1000), new BigDecimal(10)));

        // when
        List<StockCalculateResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 아무_주식도_소유하고_있지않을때_조회하면_빈_리스트가_반환된다() {
        // when
        List<StockCalculateResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).isEmpty();
    }

}
