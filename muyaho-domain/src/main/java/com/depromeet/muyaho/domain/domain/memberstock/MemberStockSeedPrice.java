package com.depromeet.muyaho.domain.domain.memberstock;

import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.common.exception.ValidationException;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * 총 매입금 (원화): 환율의 변동사항을 고려해서 구입 당시의 원화 가격(시드)를 보유.
 * 해외 주식(달러)를 원화 수익률을 계산할때 사용된다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberStockSeedPrice {

    @Column
    private BigDecimal totalPurchasePriceInWon;

    private MemberStockSeedPrice(Stock stock, BigDecimal totalPurchasePriceInWon) {
        validateTotalPurchasePrice(stock, totalPurchasePriceInWon);
        validateAvailablePrice(totalPurchasePriceInWon);
        this.totalPurchasePriceInWon = totalPurchasePriceInWon;
    }

    private void validateAvailablePrice(BigDecimal totalPurchasePrice) {
        if (totalPurchasePrice != null && totalPurchasePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(String.format("Money(%s) 은 0보다 커야합니다", totalPurchasePrice));
        }
    }

    public static MemberStockSeedPrice of(Stock stock, BigDecimal totalPurchasePrice) {
        return new MemberStockSeedPrice(stock, totalPurchasePrice);
    }

    private void validateTotalPurchasePrice(Stock stock, BigDecimal totalPurchasePrice) {
        if (stock.isTradeByDollars() && totalPurchasePrice == null) {
            throw new ValidationException(String.format("주식 타입: (%s)은 매입금이 필수로 입력되어야 합니다", stock.getType()), ErrorCode.VALIDATION_ESSENTIAL_TOTAL_PURCHASE_PRICE_EXCEPTION);
        }
    }

}
