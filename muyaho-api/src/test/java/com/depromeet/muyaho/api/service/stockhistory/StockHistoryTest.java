package com.depromeet.muyaho.api.service.stockhistory;

import com.depromeet.muyaho.api.service.MemberSetupTest;
import com.depromeet.muyaho.api.service.stockhistory.dto.request.RenewMemberStockHistoryRequest;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockHistoryTest extends MemberSetupTest {

    @Autowired
    private StockHistoryService stockHistoryService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

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
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(1000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(30);

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
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(1000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, new BigDecimal(2000), new BigDecimal(2), new BigDecimal(40)));

        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(30);

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
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(1000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(30);
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        stockHistoryService.renewMemberStockHistory(memberId, StockMarketType.BITCOIN, Collections.emptyList());

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(1);
        assertStockHistory(stockHistoryList.get(0), currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
    }

    @Test
    void 해당하는_memberStock_id와_memberId를_가진_StockHistory를_모두_제거한다() {
        // given
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        stockRepository.save(stock);
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(1000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(30);
        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        stockHistoryService.deleteMemberStockHistory(memberStock.getId(), memberId);

        // then
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).isEmpty();
    }

    private void assertStockHistory(StockHistory stockHistory, BigDecimal currentPriceInWon, BigDecimal currentPriceInDollar, BigDecimal profitOrLoseRate) {
        assertThat(stockHistory.getCurrentPriceInWon()).isEqualByComparingTo(currentPriceInWon);
        assertThat(stockHistory.getCurrentPriceInDollar()).isEqualByComparingTo(currentPriceInDollar);
        assertThat(stockHistory.getProfitOrLoseRate()).isEqualByComparingTo(profitOrLoseRate);
    }

}
