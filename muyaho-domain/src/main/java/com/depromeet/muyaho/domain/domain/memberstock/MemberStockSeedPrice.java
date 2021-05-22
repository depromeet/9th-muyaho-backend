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
