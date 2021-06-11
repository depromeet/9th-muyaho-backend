package com.depromeet.muyaho.api.controller.memberstock;

import com.depromeet.muyaho.api.controller.ApiResponse;
import com.depromeet.muyaho.api.controller.ControllerTest;
import com.depromeet.muyaho.api.controller.memberstock.api.MemberStockMockApiCaller;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryCreator;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryRepository;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.AddMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.DeleteMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.request.UpdateMemberStockRequest;
import com.depromeet.muyaho.domain.service.memberstock.dto.response.InvestStatusResponse;
import com.depromeet.muyaho.domain.service.memberstock.dto.response.MemberStockInfoResponse;
import com.depromeet.muyaho.domain.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MemberStockControllerTest extends ControllerTest {

    private MemberStockMockApiCaller memberStockMockApiCaller;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        memberStockMockApiCaller = new MemberStockMockApiCaller(mockMvc, objectMapper);
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        stockHistoryRepository.deleteAll();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("POST /api/v1/member/stock 200 OK")
    @Test
    void 새롭게_내가_보유한_주식을_등록한다() throws Exception {
        // given
        Stock stock = StockCreator.createActiveDomestic("국내주식", "국내주식 종목명");
        stockRepository.save(stock);

        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(1);
        CurrencyType currencyType = CurrencyType.WON;

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stock.getId(), purchasePrice, quantity, currencyType, null);

        // when
        ApiResponse<MemberStockInfoResponse> response = memberStockMockApiCaller.addMemberStock(request, token, 200);

        // then
        assertStockInfoResponse(response.getData().getStock(), stock.getId(), stock.getCode(), stock.getName(), stock.getType());
        assertMemberInfoResponse(response.getData(), currencyType, purchasePrice, quantity, purchasePrice.multiply(quantity));
    }

    @DisplayName("POST /api/v1/member/stock 400 VALIDATION")
    @Test
    void 해외주식_등록시_구매당시의_원화_총액을_입력해야한다() throws Exception {
        // given
        Stock stock = StockCreator.createActiveOverseas("해외주식", "해외주식 종목명");
        stockRepository.save(stock);

        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(1);
        CurrencyType currencyType = CurrencyType.DOLLAR;

        AddMemberStockRequest request = AddMemberStockRequest.testInstance(stock.getId(), purchasePrice, quantity, currencyType, null);

        // when
        ApiResponse<MemberStockInfoResponse> response = memberStockMockApiCaller.addMemberStock(request, token, 400);

        // then
        assertThat(response.getCode()).isEqualTo(ErrorCode.VALIDATION_ESSENTIAL_TOTAL_PURCHASE_PRICE_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(ErrorCode.VALIDATION_ESSENTIAL_TOTAL_PURCHASE_PRICE_EXCEPTION.getMessage());
    }

    @DisplayName("PUT /api/v1/member/stock 200 OK")
    @Test
    void 내가_보유한_주식을_수정한다() throws Exception {
        // given
        Stock stock = StockCreator.createActiveDomestic("국내주식", "국내주식 종목명");
        stockRepository.save(stock);

        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(1);
        CurrencyType currencyType = CurrencyType.WON;

        MemberStock memberStock = MemberStockCreator.createWon(testMember.getId(), stock, new BigDecimal(2000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        UpdateMemberStockRequest request = UpdateMemberStockRequest.testInstance(memberStock.getId(), purchasePrice, quantity, null);

        // when
        ApiResponse<MemberStockInfoResponse> response = memberStockMockApiCaller.updateMemberStock(request, token, 200);

        // then
        assertStockInfoResponse(response.getData().getStock(), stock.getId(), stock.getCode(), stock.getName(), stock.getType());
        assertMemberInfoResponse(response.getData(), currencyType, purchasePrice, quantity, purchasePrice.multiply(quantity));
    }

    @DisplayName("PUT /api/v1/member/stock 404 NOT FOUND")
    @Test
    void 내가_보유한_주식을_수정할때_해당하는_주식이_없는경우_404() throws Exception {
        // given
        UpdateMemberStockRequest request = UpdateMemberStockRequest.testInstance(999L, new BigDecimal(1000), new BigDecimal(1), null);

        // when
        ApiResponse<MemberStockInfoResponse> response = memberStockMockApiCaller.updateMemberStock(request, token, 404);

        // then
        assertThat(response.getCode()).isEqualTo(ErrorCode.NOT_FOUND_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @DisplayName("DELETE /api/v1/member/stock 200 OK")
    @Test
    void 내가_보유한_주식을_삭제한다() throws Exception {
        // given
        Stock stock = StockCreator.createActiveDomestic("국내주식", "국내주식 종목명");
        stockRepository.save(stock);

        MemberStock memberStock = MemberStockCreator.createWon(testMember.getId(), stock, new BigDecimal(2000), new BigDecimal(10));
        memberStockRepository.save(memberStock);

        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(memberStock.getId());

        // when
        ApiResponse<String> response = memberStockMockApiCaller.deleteMemberStock(request, token, 200);

        // then
        assertThat(response.getCode()).isEmpty();
    }

    @DisplayName("DELETE /api/v1/member/stock 404 NOTFOUND")
    @Test
    void 내가_보유한_주식을_삭제할때_해당하는_주식이_없는경우_404() throws Exception {
        // given
        DeleteMemberStockRequest request = DeleteMemberStockRequest.testInstance(999L);

        // when
        ApiResponse<String> response = memberStockMockApiCaller.deleteMemberStock(request, token, 404);

        // then
        assertThat(response.getCode()).isEqualTo(ErrorCode.NOT_FOUND_EXCEPTION.getCode());
        assertThat(response.getMessage()).isEqualTo(ErrorCode.NOT_FOUND_EXCEPTION.getMessage());
    }

    @DisplayName("GET /api/v1/member/stock/status/history 200 OK")
    @Test
    void 내가_마지막으로_조회한_주식_전체를_조회한다() throws Exception {
        // given
        BigDecimal purchasePrice = new BigDecimal(2000);
        BigDecimal quantity = new BigDecimal(10);
        BigDecimal currentPriceInWon = new BigDecimal(1000);
        BigDecimal currentPriceInDollar = new BigDecimal(1);
        BigDecimal profitOrLoseRate = new BigDecimal(10);

        Stock stock = StockCreator.createActiveDomestic("국내주식", "국내주식 종목명");
        stockRepository.save(stock);

        MemberStock memberStock = MemberStockCreator.createWon(testMember.getId(), stock, purchasePrice, quantity);
        memberStockRepository.save(memberStock);

        stockHistoryRepository.save(StockHistoryCreator.create(memberStock, currentPriceInWon, currentPriceInDollar, profitOrLoseRate));

        // when
        ApiResponse<InvestStatusResponse> response = memberStockMockApiCaller.getLastMemberInvestStatusHistory(token, 200);

        // then
        assertThat(response.getData().getFinalAsset()).isEqualTo(currentPriceInWon.multiply(quantity).toString());
        assertThat(response.getData().getSeedAmount()).isEqualTo(purchasePrice.multiply(quantity).toString());
    }

    private void assertMemberInfoResponse(MemberStockInfoResponse response, CurrencyType currencyType, BigDecimal purchasePrice,
                                          BigDecimal quantity, BigDecimal purchaseAmount) {
        assertThat(response.getCurrencyType()).isEqualTo(currencyType);
        assertThat(response.getPurchasePrice()).isEqualTo(purchasePrice.toString());
        assertThat(response.getQuantity()).isEqualTo(quantity.toString());
        assertThat(response.getPurchaseAmount()).isEqualTo(purchaseAmount.toString());
        assertThat(response.getPurchaseAmountInWon()).isNull();
    }

    private void assertStockInfoResponse(StockInfoResponse response, Long stockId, String code, String name, StockMarketType type) {
        assertThat(response.getId()).isEqualTo(stockId);
        assertThat(response.getCode()).isEqualTo(code);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getType()).isEqualTo(type);
    }

}
