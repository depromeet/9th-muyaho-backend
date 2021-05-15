package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.*;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.external.stock.StockApiCaller;
import com.depromeet.muyaho.external.stock.StockType;
import com.depromeet.muyaho.external.stock.dto.response.StockCodeResponse;
import com.depromeet.muyaho.external.stock.dto.response.StockPriceResponse;
import com.depromeet.muyaho.external.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitMarketResponse;
import com.depromeet.muyaho.external.upbit.dto.response.UpBitTradeInfoResponse;
import com.depromeet.muyaho.service.MemberSetupTest;
import com.depromeet.muyaho.service.memberstock.dto.response.MemberStockCurrentInfoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    private Stock stock;

    @BeforeEach
    void setUpStock() {
        memberStockRetrieveService = new MemberStockRetrieveService(memberStockRepository, new StubUpBitApiCaller(), new StubStockApiCaller());
        stock = stockRepository.save(StockCreator.createActive("code", "비트코인", StockMarketType.BITCOIN));
    }

    private static class StubUpBitApiCaller implements UpBitApiCaller {
        @Override
        public List<UpBitMarketResponse> retrieveMarkets() {
            return null;
        }

        @Override
        public List<UpBitTradeInfoResponse> retrieveTrades(String marketCode) {
            return Collections.singletonList(UpBitTradeInfoResponse.testInstance(marketCode, 20000.5));
        }
    }

    private static class StubStockApiCaller implements StockApiCaller {
        @Override
        public List<StockCodeResponse> getStockCodes(StockType type) {
            return null;
        }

        @Override
        public List<StockPriceResponse> getStockPrice(String code) {
            return Collections.singletonList(StockPriceResponse.testInstance(code, 30000));
        }
    }

    @AfterEach
    void cleanUP() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    void 내가_소유한_비트코인_현재가를_조회한다() {
        // given
        String code = "KRW-BIT";
        String name = "비트코인";
        StockMarketType type = StockMarketType.BITCOIN;
        Stock stock = StockCreator.createActive(code, name, type);
        stockRepository.save(stock);

        double purchasePrice = 10000;
        double quantity = 10;
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, purchasePrice, quantity);
        memberStockRepository.save(memberStock);

        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertMemberStockInfo(responses.get(0), memberStock.getId(), code, name, type);
        assertStockPrice(responses.get(0), "10000", "10", "100000", "20000.5", "200005");
    }

    @Test
    void 내가보유한_국내주식_현재가를_조회한다() {
        // given
        String code = "code";
        String name = "NC";
        StockMarketType type = StockMarketType.DOMESTIC_STOCK;
        Stock stock = StockCreator.createActive(code, name, type);
        stockRepository.save(stock);

        double purchasePrice = 20000;
        double quantity = 20;
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, purchasePrice, quantity);
        memberStockRepository.save(memberStock);

        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.DOMESTIC_STOCK, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertMemberStockInfo(responses.get(0), memberStock.getId(), code, name, type);
        assertStockPrice(responses.get(0), "20000", "20", "400000", "30000", "600000");
    }

    @Test
    void 내가보유한_해외주식_현재가를_조회한다() {
        // given
        String code = "code";
        String name = "APPLE";
        StockMarketType type = StockMarketType.OVERSEAS_STOCK;
        Stock stock = StockCreator.createActive(code, name, type);
        stockRepository.save(stock);

        double purchasePrice = 40000;
        double quantity = 10;
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, purchasePrice, quantity);
        memberStockRepository.save(memberStock);

        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.OVERSEAS_STOCK, memberId);

        // then
        assertThat(responses).hasSize(1);
        assertMemberStockInfo(responses.get(0), memberStock.getId(), code, name, type);
        assertStockPrice(responses.get(0), "40000", "10", "400000", "30000", "300000");
    }

    @Test
    void 다른_사람이_소유한_주식에_접근할_수없다() {
        // given
        memberStockRepository.save(MemberStockCreator.create(999L, stock, 10000, 10));

        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 아무_주식도_소유하고_있지않을때_조회하면_빈_리스트가_반환된다() {
        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMemberCurrentStocks(StockMarketType.BITCOIN, memberId);

        // then
        assertThat(responses).isEmpty();
    }

    private void assertMemberStockInfo(MemberStockCurrentInfoResponse response, Long id, String code, String name, StockMarketType type) {
        assertThat(response.getMemberStockId()).isEqualTo(id);
        assertThat(response.getStock().getCode()).isEqualTo(code);
        assertThat(response.getStock().getName()).isEqualTo(name);
        assertThat(response.getStock().getType()).isEqualTo(type);
    }

    private void assertStockPrice(MemberStockCurrentInfoResponse response, String purchasePrice, String quantity, String purchaseAmount, String current, String currentAmount) {
        assertThat(response.getPurchasePrice()).isEqualTo(purchasePrice);
        assertThat(response.getQuantity()).isEqualTo(quantity);
        assertThat(response.getPurchaseAmount()).isEqualTo(purchaseAmount);
        assertThat(response.getCurrentPrice()).isEqualTo(current);
        assertThat(response.getCurrentAmount()).isEqualTo(currentAmount);
    }

}
