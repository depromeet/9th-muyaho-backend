package com.depromeet.muyaho.domain.service.memberstock.dto.request;

import com.depromeet.muyaho.domain.domain.common.CurrencyType;
import com.depromeet.muyaho.domain.domain.memberstock.MemberStock;
import com.depromeet.muyaho.domain.domain.stock.Stock;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddMemberStockRequest {

    @NotNull(message = "{stock.id.notnull}")
    private Long stockId;

    @NotNull(message = "{stock.purchasePrice.notnull}")
    private BigDecimal purchasePrice;

    @NotNull(message = "{stock.quantity.notnull}")
    private BigDecimal quantity;

    @NotNull(message = "{stock.currencyType.notnull}")
    private CurrencyType currencyType;

    private BigDecimal purchaseTotalPrice;

    public MemberStock toEntity(Long memberId, Stock stock) {
        return MemberStock.of(memberId, stock, purchasePrice, quantity, currencyType, purchaseTotalPrice);
    }

    public static AddMemberStockRequest testInstance(Long stockId, BigDecimal purchasePrice, BigDecimal quantity, CurrencyType currencyType, BigDecimal purchaseTotalPrice) {
        return new AddMemberStockRequest(stockId, purchasePrice, quantity, currencyType, purchaseTotalPrice);
    }

}
