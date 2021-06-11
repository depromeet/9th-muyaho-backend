package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.common.exception.ValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quantity {

    @Column(nullable = false)
    private BigDecimal quantity;

    private Quantity(BigDecimal quantity) {
        validateValidQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateValidQuantity(BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(String.format("주식은 0개 이상 보유할 수 있습니다. (현재 입력값: %s)", quantity), ErrorCode.VALIDATION_INVALID_QUANTITY_EXCEPTION);
        }
    }

    public static Quantity of(BigDecimal quantity) {
        return new Quantity(quantity);
    }

}
