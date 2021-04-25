package com.depromeet.muyaho.service.memberstock;

import com.depromeet.muyaho.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.stock.Stock;
import com.depromeet.muyaho.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.stock.StockRepository;
import com.depromeet.muyaho.service.MemberSetupTest;
import com.depromeet.muyaho.service.memberstock.dto.request.AddMemberStockRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberStockServiceTest extends MemberSetupTest {

    @Autowired
    private MemberStockService memberStockService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    private Long stockId;

    @BeforeEach
    void setUpStock() {
        Stock stock = stockRepository.save(StockCreator.createActive("code", "비트코인", StockMarketType.BITCOIN));
        stockId = stock.getId();
    }

    @AfterEach
    void cleanUP() {
        super.cleanup();
        stockRepository.deleteAll();
        memberStockRepository.deleteAll();
    }

    @Test
    void 멤버가_새롭게_소유한_주식을_등록한다() {
        // given
        int purchasePrice = 10000;
        int quantity = 5;

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stockId, purchasePrice, quantity);

        // when
        memberStockService.addMemberStock(request, memberId);

        // then
        List<MemberStock> memberStockList = memberStockRepository.findAll();
        assertThat(memberStockList).hasSize(1);
        assertMemberStock(memberStockList.get(0), memberId, stockId, purchasePrice, quantity);
    }

    @Test
    void 존재하지_않은_주식을_소유한_주식으로_등록하려하면_에러가_발생한다() {
        // given
        Long notExistStockId = 999L;

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(notExistStockId, 10000, 10);

        // when & then
        assertThatThrownBy(() -> memberStockService.addMemberStock(request, memberId)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_소유한_주식으로_등록한_주식을_다시_등록하려하면_에러가_발생한다() {
        // given
        memberStockRepository.save(MemberStockCreator.create(memberId, stockId, 10000, 10));

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stockId, 1000, 1);

        // when & then
        assertThatThrownBy(() -> memberStockService.addMemberStock(request, memberId)).isInstanceOf(IllegalArgumentException.class);
    }

    private void assertMemberStock(MemberStock memberStock, Long memberId, Long stockId, int purchasePrice, int quantity) {
        assertThat(memberStock.getMemberId()).isEqualTo(memberId);
        assertThat(memberStock.getStockId()).isEqualTo(stockId);
        assertThat(memberStock.getPurchasePrice()).isEqualTo(purchasePrice);
        assertThat(memberStock.getQuantity()).isEqualTo(quantity);
    }

}
