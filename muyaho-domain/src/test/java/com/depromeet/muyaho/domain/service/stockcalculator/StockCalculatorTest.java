package com.depromeet.muyaho.domain.service.stockcalculator;

import com.depromeet.muyaho.domain.service.stock.dto.response.StockInfoResponse;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCurrentPriceResponse;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockPurchaseResponse;
import com.depromeet.muyaho.domain.service.stockcalculator.dto.response.StockCalculateResponse;
import com.depromeet.muyaho.domain.service.stockhistory.StockHistoryService;
import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCollection;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockCreator;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStockRepository;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import com.depromeet.muyaho.domain.domain.stock.StockCreator;
import com.depromeet.muyaho.domain.domain.stock.StockMarketType;
import com.depromeet.muyaho.domain.domain.stock.StockRepository;
import com.depromeet.muyaho.domain.service.MemberSetupTest;
import com.depromeet.muyaho.domain.domain.stockhistory.StockHistoryRepository;
import com.depromeet.muyaho.external.client.bitcoin.upbit.UpBitApiCaller;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitCodesResponse;
import com.depromeet.muyaho.external.client.bitcoin.upbit.dto.response.UpBitPriceResponse;
import com.depromeet.muyaho.external.client.currency.ExchangeRateApiCaller;
import com.depromeet.muyaho.external.client.stock.StockApiCaller;
import com.depromeet.muyaho.external.client.stock.StockType;
import com.depromeet.muyaho.external.client.stock.dto.response.StockCodesResponse;
import com.depromeet.muyaho.external.client.stock.dto.response.StockPriceResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockCalculatorTest extends MemberSetupTest {

    private StockCalculator stockCalculator;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MemberStockRepository memberStockRepository;

    @Autowired
    private StockHistoryService stockHistoryService;

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    private static final BigDecimal bitCoinCurrentPrice = new BigDecimal(1000);
    private static final BigDecimal stockCurrentPrice = new BigDecimal(3000);
    private static final BigDecimal currencyChangeRate = new BigDecimal(1000);

    @BeforeEach
    void setUpStock() {
        stockCalculator = new StockCalculatorImpl(new StubUpBitApiCaller(), new StubStockApiCaller(), new StubExchangeAPiCaller(), stockHistoryService);
    }

    @AfterEach
    void cleanUp() {
        super.cleanup();
        stockHistoryRepository.deleteAllInBatch();
        memberStockRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    private static class StubUpBitApiCaller implements UpBitApiCaller {
        @Override
        public List<UpBitCodesResponse> fetchListedBitcoins() {
            return null;
        }

        @Override
        public List<UpBitPriceResponse> fetchCurrentBitcoinPrice(String codes) {
            return Collections.singletonList(UpBitPriceResponse.testInstance(codes, bitCoinCurrentPrice));
        }
    }

    private static class StubStockApiCaller implements StockApiCaller {
        @Override
        public List<StockCodesResponse> fetchListedStocksCodes(StockType type) {
            return null;
        }

        @Override
        public List<StockPriceResponse> fetchCurrentStockPrice(String codes) {
            return Collections.singletonList(StockPriceResponse.testInstance(codes, stockCurrentPrice));
        }
    }

    private static class StubExchangeAPiCaller implements ExchangeRateApiCaller {
        @Override
        public BigDecimal fetchExchangeRate() {
            return currencyChangeRate;
        }
    }

    @MethodSource("???_?????????_??????")
    @ParameterizedTest
    void ?????????_??????_?????????_????????????_?????????_?????????_????????????(String code, String name, StockMarketType type, CurrencyType currencyType) {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(10);

        Stock stock = stockRepository.save(StockCreator.createActive(code, name, type));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, stock, purchasePrice, quantity, currencyType)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, type, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockInfoResponse(responses.get(0).getStock(), stock.getId(), stock.getCode(), stock.getName(), stock.getType());
    }

    @MethodSource("???_?????????_??????")
    @ParameterizedTest
    void ?????????_??????_?????????_??????_?????????_????????????_????????????(String code, String name, StockMarketType type, CurrencyType currencyType) {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(10);

        Stock stock = stockRepository.save(StockCreator.createActive(code, name, type));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, stock, purchasePrice, quantity, currencyType)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, type, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCalculateResponse(responses.get(0), memberStock.getId(), quantity, currencyType);
        assertStockPurchaseResponse(responses.get(0).getPurchase(), purchasePrice, purchasePrice.multiply(quantity), memberStock.getPurchaseTotalPriceInWon());
    }

    private static Stream<Arguments> ???_?????????_??????() {
        return Stream.of(
            Arguments.of("KRW-code", "????????????", StockMarketType.BITCOIN, CurrencyType.WON),
            Arguments.of("???????????? ?????? ??????", "???????????? ?????? ???", StockMarketType.DOMESTIC_STOCK, CurrencyType.WON),
            Arguments.of("???????????? ?????? ??????", "???????????? ?????????", StockMarketType.OVERSEAS_STOCK, CurrencyType.DOLLAR));
    }

    @DisplayName("???????????? ????????? ????????? ??????????????? ???????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_??????_?????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(10);

        Stock bitCoin = stockRepository.save(StockCreator.createActive("KRW-code", "????????????", StockMarketType.BITCOIN));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, bitCoin, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.BITCOIN, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getWon(), bitCoinCurrentPrice, bitCoinCurrentPrice.multiply(quantity));
    }

    @DisplayName("???????????? ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_??????_?????????_?????????_????????????_??????_?????????_????????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(10);

        Stock bitCoin = stockRepository.save(StockCreator.createActive("KRW-code", "????????????", StockMarketType.BITCOIN));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, bitCoin, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.BITCOIN, collection);

        // then
        assertThat(responses).hasSize(1);
        BigDecimal expectedCurrentDollar = bitCoinCurrentPrice.divide(currencyChangeRate, new MathContext(2)); // ????????? (??????) = ????????? (??????) / ??????
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getDollar(), expectedCurrentDollar, expectedCurrentDollar.multiply(quantity));
    }

    @DisplayName("???????????? ????????? ????????? ??????????????? ???????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_?????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(100);
        BigDecimal quantity = new BigDecimal(20);

        Stock stock = stockRepository.save(StockCreator.createActive("NC", "????????????-NC", StockMarketType.DOMESTIC_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, stock, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.DOMESTIC_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getWon(), stockCurrentPrice, stockCurrentPrice.multiply(quantity));
    }

    @DisplayName("???????????? ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_??????_?????????_?????????_????????????_??????_?????????_????????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(100);
        BigDecimal quantity = new BigDecimal(20);

        Stock stock = stockRepository.save(StockCreator.createActive("NC", "????????????-NC", StockMarketType.DOMESTIC_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.create(memberId, stock, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.DOMESTIC_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        BigDecimal expectedCurrentDollar = stockCurrentPrice.divide(currencyChangeRate, new MathContext(2)); // ????????? (??????) = ????????? (??????) / ??????
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getDollar(), expectedCurrentDollar, expectedCurrentDollar.multiply(quantity));
    }

    @DisplayName("???????????? ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_??????_?????????_?????????_????????????_??????_?????????_????????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(20);

        Stock stock = stockRepository.save(StockCreator.createActive("???????????? ?????? ??????", "APPLE", StockMarketType.OVERSEAS_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.createDollar(memberId, stock, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.OVERSEAS_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        BigDecimal expectedCurrentWon = stockCurrentPrice.multiply(currencyChangeRate); // ????????? (??????) = ????????? (??????) * ??????
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getWon(), expectedCurrentWon, expectedCurrentWon.multiply(quantity));
    }

    @DisplayName("???????????? ????????? ????????? ??????????????? ???????????? ????????? ???????????? ????????????")
    @Test
    void ?????????_????????????_?????????_??????_??????_?????????_????????????() {
        // given
        BigDecimal purchasePrice = new BigDecimal(1000);
        BigDecimal quantity = new BigDecimal(20);

        Stock stock = stockRepository.save(StockCreator.createActive("???????????? ?????? ??????", "APPLE", StockMarketType.OVERSEAS_STOCK));
        MemberStock memberStock = memberStockRepository.save((MemberStockCreator.createDollar(memberId, stock, purchasePrice, quantity)));
        MemberStockCollection collection = MemberStockCollection.of(Collections.singletonList(memberStock));

        // when
        List<StockCalculateResponse> responses = stockCalculator.calculateCurrentMemberStocks(memberId, StockMarketType.OVERSEAS_STOCK, collection);

        // then
        assertThat(responses).hasSize(1);
        assertStockCurrentPriceResponse(responses.get(0).getCurrent().getDollar(), stockCurrentPrice, stockCurrentPrice.multiply(quantity));
    }

    private void assertStockCurrentPriceResponse(StockCurrentPriceResponse won, BigDecimal currentWonPrice, BigDecimal currentWonAmount) {
        assertThat(won.getUnitPrice()).isEqualTo(currentWonPrice.toString());
        assertThat(won.getAmountPrice()).isEqualTo(currentWonAmount.toString());
    }

    private void assertStockInfoResponse(StockInfoResponse response, Long stockId, String code, String name, StockMarketType type) {
        assertThat(response.getId()).isEqualTo(stockId);
        assertThat(response.getCode()).isEqualTo(code);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getType()).isEqualTo(type);
    }

    private void assertStockCalculateResponse(StockCalculateResponse response, Long memberStockId, BigDecimal quantity, CurrencyType type) {
        assertThat(response.getMemberStockId()).isEqualTo(memberStockId);
        assertThat(response.getQuantity()).isEqualTo(quantity.toString());
        assertThat(response.getCurrencyType()).isEqualTo(type);
    }

    private void assertStockPurchaseResponse(StockPurchaseResponse response, BigDecimal purchasePrice, BigDecimal amountPrice, BigDecimal amountInWon) {
        assertThat(response.getUnitPrice()).isEqualTo(purchasePrice.toString());
        assertThat(response.getAmount()).isEqualTo(amountPrice.toString());
        assertThat(response.getAmountInWon()).isEqualTo(amountInWon == null ? null : amountInWon.toString());
    }

}
