package com.depromeet.muyaho.api.service.memberstock;

import com.depromeet.muyaho.api.service.stockcalculator.StockCalculator;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.domain.memberstock.*;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.api.service.MemberSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberStockRetrieveServiceTest extends MemberSetupTest {

    private MemberStockRetrieveService memberStockRetrieveService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        memberStockRetrieveService = new MemberStockRetrieveService(memberStockRepository, new StubStockCalculator());
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    private static class StubStockCalculator implements StockCalculator {
        @Override
        public List<StockCalculateResponse> calculateCurrentStocks(StockMarketType type, MemberStockCollection collection) {
            return Collections.singletonList(StockCalculateResponse.testInstance(100L, new BigDecimal(10), new BigDecimal(1000), new BigDecimal(3000)));
        }
    }

    @Test
    void 내가_보유한_주식들을_현재가를_조회한다() {
        // given
        StockMarketType type = StockMarketType.DOMESTIC_STOCK;
        Stock stock = stockRepository.save(StockCreator.createActive("code", "name", type));
        memberStockRepository.save(MemberStockCreator.create(memberId, stock, 2000, 10));

        // when
        List<StockCalculateResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(type, memberId);

        // then
        assertThat(responses).hasSize(1);
    }

    @Test
    void 요청한_주식_타입만_조회된다() {
        // given
        Stock stock = stockRepository.save(StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK));
        memberStockRepository.save(MemberStockCreator.create(memberId, stock, 2000, 10));

        // when
        List<StockCalculateResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 다른_사람이_소유한_주식에_접근할_수없다() {
        // given
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        memberStockRepository.save(MemberStockCreator.create(999L, stock, 10000, 10));

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
