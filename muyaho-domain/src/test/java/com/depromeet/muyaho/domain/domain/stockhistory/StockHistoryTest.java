package com.depromeet.muyaho.domain.domain.stockhistory;

import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StockHistoryTest {

    private MemberStock memberStock;

    @BeforeEach
    void setUp() {
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        memberStock = MemberStockCreator.create(100L, stock, new BigDecimal(1000), new BigDecimal(20));
    }

    @Test
    void 동등성_테스트() {
        // given
        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(20);

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);

        // then
        assertThat(stockHistory1).isEqualTo(stockHistory2);
    }

    @Test
    void 동등성_테스트_1() {
        // given
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        MemberStock memberStock1 = MemberStockCreator.create(100L, stock, new BigDecimal(1000), new BigDecimal(20));

        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(20);

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock1, currentPriceInWon, currentPriceInDollar, profitOrLoseRate);

        // then
        assertThat(stockHistory1).isEqualTo(stockHistory2);
    }

    @Test
    void 동등성_테스트_소수점이_달라도_같다() {
        // given
        Stock stock = StockCreator.createActive("code", "name", StockMarketType.DOMESTIC_STOCK);
        MemberStock memberStock1 = MemberStockCreator.create(100L, stock, new BigDecimal(1000), new BigDecimal(20));

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, new BigDecimal(1000), new BigDecimal(1), new BigDecimal(20));
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock1, new BigDecimal("1000.0"), new BigDecimal("1.0"), new BigDecimal("20.0"));

        // then
        assertThat(stockHistory1).isEqualTo(stockHistory2);
    }

    @Test
    void 동등성_테스트_수익률만_다를경우() {
        // given
        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, new BigDecimal(20));
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, new BigDecimal(30));

        // then
        assertThat(stockHistory1).isNotEqualTo(stockHistory2);
    }

    @Test
    void 동등성_테스트_현재_원화가_다를경우() {
        // given
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(20);

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, new BigDecimal(1000), currentPriceInDollar, profitOrLoseRate);
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock, new BigDecimal(1001), currentPriceInDollar, profitOrLoseRate);

        // then
        assertThat(stockHistory1).isNotEqualTo(stockHistory2);
    }

    @Test
    void 동등성_테스트_현재_달러가_다를경우() {
        // given
        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal profitOrLoseRate = new BigDecimal(20);

        // when
        StockHistory stockHistory1 = StockHistoryCreator.create(memberStock, currentPriceInWon, new BigDecimal(1), profitOrLoseRate);
        StockHistory stockHistory2 = StockHistoryCreator.create(memberStock, currentPriceInWon, new BigDecimal(2), profitOrLoseRate);

        // then
        assertThat(stockHistory1).isNotEqualTo(stockHistory2);
    }

}
