package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.*;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockRepository;
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
        memberStockRetrieveService = new MemberStockRetrieveService(memberStockRepository, new StubUpBitApiCaller());
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

    @AfterEach
    void cleanUP() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    void 내가_소유한_주식_들을_조회하면_보유한_주식_정보와함께_조회된다() {
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
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMyBitCoinStock(memberId);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getMemberStockId()).isEqualTo(memberStock.getId());
        assertThat(responses.get(0).getStock().getCode()).isEqualTo(code);
        assertThat(responses.get(0).getStock().getName()).isEqualTo(name);
        assertThat(responses.get(0).getStock().getType()).isEqualTo(type);

        assertThat(responses.get(0).getPurchasePrice()).isEqualTo("10000");
        assertThat(responses.get(0).getQuantity()).isEqualTo("10");
        assertThat(responses.get(0).getPurchaseAmount()).isEqualTo("100000");
        assertThat(responses.get(0).getCurrentPrice()).isEqualTo("20000.5");
        assertThat(responses.get(0).getCurrentAmount()).isEqualTo("200005");
    }

    @Test
    void 다른_사람이_소유한_주식에_접근할_수없다() {
        // given
        memberStockRepository.save(MemberStockCreator.create(999L, stock, 10000, 10));

        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMyBitCoinStock(memberId);

        // then
        assertThat(responses).isEmpty();
    }

    @Test
    void 아무_주식도_소유하고_있지않을때_조회하면_빈_리스트가_반환된다() {
        // when
        List<MemberStockCurrentInfoResponse> responses = memberStockRetrieveService.getMyBitCoinStock(memberId);

        // then
        assertThat(responses).isEmpty();
    }

}
