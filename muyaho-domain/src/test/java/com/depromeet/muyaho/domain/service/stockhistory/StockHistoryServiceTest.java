package com.depromeet.muyaho.domain.service.stockhistory;

import com.depromeet.muyaho.domain.service.MemberSetupTest;
import com.depromeet.muyaho.domain.service.stockhistory.dto.request.RenewMemberStockHistoryRequest;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistory;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryCreator;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryRepository;
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
class StockHistoryServiceTest extends MemberSetupTest {

    @Autowired
    private StockHistoryService stockHistoryService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    private MemberStock memberStock;

    private static final BigDecimal currentPriceInWon = new BigDecimal(1000);
    private static final BigDecimal currentPriceInDollar = new BigDecimal(1);
    private static final BigDecimal profitOrLoseRate = new BigDecimal(30);

    @BeforeEach
    void setUp() {
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(1000), new BigDecimal(10));
        memberStockRepository.save(memberStock);
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        stockHistoryRepository.deleteAllInBatch();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    void 멤버가_조회한_주식_결과를_저장해둔다() {
        // given
        RenewMemberStockHistoryRequest request = RenewMemberStockHistoryRequest.testInstance(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);

        // when
        stockHistoryService.renewMemberStockHistory(memberId, StockMarketType.DOMESTIC_STOCK, Collections.singletonList(request));

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    @Test
    void 멤버가_조회한_주식결과를_갱신하면_같은_주식_타입의기존의_기록은_지워진다() {
        // given
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, new BigDecimal(2000), new BigDecimal(2), new BigDecimal(40)));

        RenewMemberStockHistoryRequest request = RenewMemberStockHistoryRequest.testInstance(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);

        // when
        stockHistoryService.renewMemberStockHistory(memberId, StockMarketType.DOMESTIC_STOCK, Collections.singletonList(request));

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    @Test
    void 멤버가_조회한_주식결과를_갱신할때_같은_값이면_삭제되지_않는다() {
        // given
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        RenewMemberStockHistoryRequest request = RenewMemberStockHistoryRequest.testInstance(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);

        // when
        stockHistoryService.renewMemberStockHistory(memberId, StockMarketType.DOMESTIC_STOCK, Collections.singletonList(request));

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    @Test
    void 주식_기록을_갱신할때_다른_주식_타입의_주식_기록들은_지워지지_않는다() {
        // given
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        stockHistoryService.renewMemberStockHistory(memberId, StockMarketType.BITCOIN, Collections.emptyList());

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    @Test
    void 사용자의_해당하는_MemberStock에_해당하는_StockHistory를_모두_제거한다() {
        // given
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        stockHistoryService.deleteMemberStockHistory(memberStock.getId(), memberId);

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).isEmpty();
    }

    @Test
    void 다른_사용자의_StockHistory를_제거할수_없다() {
        // given
        Long anotherMemberId = 999L;
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        stockHistoryService.deleteMemberStockHistory(memberStock.getId(), anotherMemberId);

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    private void assertStockHistory(StockHistory stockHistory, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        assertThat(stockHistory.getCurrentPriceInWon()).isEqualByComparingTo(currentPriceInWon);
        assertThat(stockHistory.getCurrentPriceInDollar()).isEqualByComparingTo(currentPriceInDollar);
        assertThat(stockHistory.getProfitOrLoseRate()).isEqualByComparingTo(profitOrLoseRate);
    }

}
