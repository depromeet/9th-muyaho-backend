package com.depromeet.muyaho.api.service.stockcalculator;

import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCurrentResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockPurchaseResponse;
import com.depromeet.muyaho.api.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.api.service.MemberSetupTest;
import com.depromeet.muyaho.external.client.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitTradeInfoResponse;
import com.depromeet.muyaho.external.client.stock.StockApiCaller;
import com.depromeet.muyaho.external.client.stock.StockType;
import com.depromeet.muyaho.external.client.stock.dto.response.StockCodeResponse;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;
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
public class StockCalculatorTest extends MemberSetupTest {

    private StockCalculator stockCalculator;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @BeforeEach
    void setUpStock() {
        stockCalculator = new StockCalculatorImpl(new StubUpBitApiCaller(), new StubStockApiCaller());
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    private static class StubUpBitApiCaller implements UpBitApiCaller {
        @Override
        public List<UpBitMarketResponse> retrieveMarkets() {
            return null;
        }

        @Override
        public List<UpBitTradeInfoResponse> retrieveTrades(String marketCode) {
            return Collections.singletonList(UpBitTradeInfoResponse.testInstance(marketCode, new BigDecimal(2000)));
        }
    }

    private static class StubStockApiCaller implements StockApiCaller {
        @Override
        public List<StockCodeResponse> getStockCodes(StockType type) {
            return null;
        }

        @Override
        public List<StockPriceResponse> getStockPrice(String code) {
            return Collections.singletonList(StockPriceResponse.testInstance(code, new BigDecimal(300)));
        }
    }

    @Test
    void 보유한_비트코인의_현재가를_가져와서_수익률을_계산한다() {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(10);

        Stock bitCoin = stockRepository.save(StockCreator.createActive("KRW-code", "비트코인", StockMarketType.BITCOIN));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, bitCoin, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentStocks(StockMarketType.BITCOIN, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCalculateResponse(responses.get(0), memberStock.getId(), "1000", "10", "10000", "2000", "20000", "100");
    }

    @Test
    void 보유한_국내주식의_현재가를_가져와서_수익률을_계산한다() {
        // given
        BigDecimal purchasePrice = new BigDecimal(100);
        BigDecimal quantity = new BigDecimal(20);

        Stock bitCoin = stockRepository.save(StockCreator.createActive("NC", "국내주식-NC", StockMarketType.DOMESTIC_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, bitCoin, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentStocks(StockMarketType.DOMESTIC_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCalculateResponse(responses.get(0), memberStock.getId(), "100", "20", "2000", "300", "6000", "200");
    }

    @Test
    void 보유한_해외주식의_현재가를_가져와서_수익률을_계산한다() {
        // given
        BigDecimal purchasePrice = new BigDecimal(100);
        BigDecimal quantity = new BigDecimal(20);

        Stock bitCoin = stockRepository.save(StockCreator.createActive("APPLE", "해외주식-APPLE", StockMarketType.OVERSEAS_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.createDollar(memberId, bitCoin, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentStocks(StockMarketType.OVERSEAS_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCalculateResponse(responses.get(0), memberStock.getId(), "100", "20", "2000", "300", "6000", "200");
    }

    private void assertStockCalculateResponse(StockCalculateResponse response, Long memberStockId, String purchasePrice, String quantity,
                                              String purchaseAmount, String currentPrice, String currentAmount, String earningRate) {
        assertThat(response.getMemberStockId()).isEqualTo(memberStockId);
        assertThat(response.getQuantity()).isEqualTo(quantity);
        assertStockPurchaseResponse(response.getPurchase(), purchasePrice, purchaseAmount);
        assertStockCurrentResponse(response.getCurrent(), currentPrice, currentAmount);
        assertThat(response.getEarningRate()).isEqualTo(earningRate);
    }

    private void assertStockPurchaseResponse(StockPurchaseResponse response, String unitPrice, String amount) {
        assertThat(response.getUnitPrice()).isEqualTo(unitPrice);
        assertThat(response.getAmount()).isEqualTo(amount);
    }

    private void assertStockCurrentResponse(StockCurrentResponse response, String unitPrice, String amount) {
        assertThat(response.getUnitPrice()).isEqualTo(unitPrice);
        assertThat(response.getAmount()).isEqualTo(amount);
    }

}
