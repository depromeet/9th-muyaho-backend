package com.depromeet.muyaho.domain.service.memberstock;

import com.depromeet.muyaho.domain.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.common.exception.ConflictException;
import com.depromeet.muyaho.common.exception.NotFoundException;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.*;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.service.MemberSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberStockServiceTest extends MemberSetupTest {

    @Autowired
    private MemberStockService memberStockService;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private DeletedMemberSockRepository deletedMemberSockRepository;

    private Stock stock;

    private static final BigDecimal purchasePrice = new BigDecimal(10000);
    private static final BigDecimal quantity = new BigDecimal(5);
    private MemberStock memberStock;

    @BeforeEach
    void setUpStock() {
        stock = stockRepository.save(StockCreator.createActive("code", "비트코인", StockMarketType.BITCOIN));
        memberStock = MemberStockCreator.create(memberId, stock, purchasePrice, quantity);
    }

    @AfterEach
    void cleanUP() {
        super.cleanup();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
        deletedMemberSockRepository.deleteAll();
    }

    @Test
    void 멤버가_새롭게_보유한_주식을_등록한다() {
        // given
        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stock.getId(), purchasePrice, quantity, CurrencyType.WON, null);

        // when
        memberStockService.addMemberStock(request, memberId);

        // then
        List<MemberStock> memberStockList = memberStockRepository.findAll();
        assertThat(memberStockList).hasSize(1);
        assertMemberStock(memberStockList.get(0), memberId, stock.getId(), purchasePrice, quantity);
    }

    @Test
    void 존재하지_않은_주식을_소유한_주식으로_등록하려하면_404_에러가_발생한다() {
        // given
        Long notExistStockId = 999L;

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(notExistStockId, purchasePrice, quantity, CurrencyType.WON, null);

        // when & then
        assertThatThrownBy(() -> memberStockService.addMemberStock(request, memberId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 이미_소유한_주식으로_등록한_주식을_다시_등록하려하면_409_에러가_발생한다() {
        // given
        memberStockRepository.save(memberStock);

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stock.getId(), purchasePrice, quantity, CurrencyType.WON, null);

        // when & then
        assertThatThrownBy(() -> memberStockService.addMemberStock(request, memberId)).isInstanceOf(ConflictException.class);
    }

    @Test
    void 비활성화된_주식을_등록하려는_경우_404_에러가_발생한다() {
        // given
        Stock disActiveStock = stockRepository.save(StockCreator.createDisable("Disable", "비활성화", StockMarketType.BITCOIN));
        AddMemberStockRequest request = AddMemberStockRequest.testInstance(disActiveStock.getId(), purchasePrice, quantity, CurrencyType.WON, null);

        // when
        assertThatThrownBy(() -> memberStockService.addMemberStock(request, memberId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 보유한_주식의_보유량과_평단가를_수정하면_DB_에_반영된다() {
        // given
        MemberStock memberStock = MemberStockCreator.create(memberId, stock, new BigDecimal(10000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        UpdateMemberStockRequest request = UpdateMemberStockRequest.testInstance(memberStock.getId(), purchasePrice, quantity, null);

        // when
        memberStockService.updateMemberStock(request, memberId);

        // then
        List<MemberStock> memberStockList = memberStockRepository.findAll();
        assertThat(memberStockList).hasSize(1);
        assertMemberStock(memberStockList.get(0), memberId, stock.getId(), purchasePrice, quantity);
    }

    @Test
    void 다른_사람이_소유한_주식에_대해서_수정할_수_없다() {
        // given
        memberStockRepository.save(memberStock);

        UpdateMemberStockRequest request = UpdateMemberStockRequest.testInstance(memberStock.getId(), purchasePrice, quantity, null);

        // when
        assertThatThrownBy(() -> memberStockService.updateMemberStock(request, 999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 회원이_보유하지_않은_주식에대해서_수정할_수없다() {
        // given
        UpdateMemberStockRequest request = UpdateMemberStockRequest.testInstance(999L, new BigDecimal(10000), new BigDecimal(10), null);

        // when
        assertThatThrownBy(() -> memberStockService.updateMemberStock(request, memberId)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 내가_등록한_보유_주식을_등록_해제하면_기존의_DB_테이블에서_삭제된다() {
        // given
        memberStockRepository.save(memberStock);

        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(memberStock.getId());

        // when
        memberStockService.deleteMemberStock(request, memberId);

        // then
        List<MemberStock> memberStockList = memberStockRepository.findAll();
        assertThat(memberStockList).isEmpty();
    }

    @Test
    void 내가_등록한_보유_주식을_삭제하면_기존의_id와_해당_정보들이_백업_테이블로_자동으로_백업된다() {
        // given
        memberStockRepository.save(memberStock);

        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(memberStock.getId());

        // when
        memberStockService.deleteMemberStock(request, memberId);

        // then
        List<DeletedMemberStock> deletedMemberStocks = deletedMemberSockRepository.findAll();
        assertThat(deletedMemberStocks).hasSize(1);
        assertDeletedMemberStack(deletedMemberStocks.get(0), memberStock.getId(), stock.getId(), memberId, purchasePrice, quantity);
    }

    @Test
    void 다른사람이_소유한_보유_주식을_삭제할수없다() {
        // given
        memberStockRepository.save(memberStock);

        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(memberStock.getId());

        // when & then
        assertThatThrownBy(() -> memberStockService.deleteMemberStock(request, 999L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 소유하지_않은_보유_주식을_삭제할수없다() {
        // given
        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(999L);

        // when & then
        assertThatThrownBy(() -> memberStockService.deleteMemberStock(request, memberId)).isInstanceOf(NotFoundException.class);
    }

    private void assertDeletedMemberStack(DeletedMemberStock deletedMemberStock, Long memberStockId, Long stockId, Long memberId, BigDecimal purchasePrice, BigDecimal quantity) {
        assertThat(deletedMemberStock.getBackupId()).isEqualTo(memberStockId);
        assertThat(deletedMemberStock.getStockId()).isEqualTo(stockId);
        assertThat(deletedMemberStock.getMemberId()).isEqualTo(memberId);
        assertThat(deletedMemberStock.getStockAmount().getPurchaseUnitPrice()).isEqualByComparingTo(purchasePrice);
        assertThat(deletedMemberStock.getStockAmount().getQuantity()).isEqualByComparingTo(quantity);
    }

    private void assertMemberStock(MemberStock memberStock, Long memberId, Long stockId, BigDecimal purchasePrice, BigDecimal quantity) {
        assertThat(memberStock.getMemberId()).isEqualTo(memberId);
        assertThat(memberStock.getStock().getId()).isEqualTo(stockId);
        assertThat(memberStock.getPurchaseUnitPrice()).isEqualByComparingTo(purchasePrice);
        assertThat(memberStock.getQuantity()).isEqualByComparingTo(quantity);
    }

}
