package com.depromeet.muyaho.domain.domain.common;

import com.depromeet.muyaho.common.exception.ErrorCode;
import com.depromeet.muyaho.common.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quantity {

    @Column(nullable = false)
    private BigDecimal quantity;

    private Quantity(double quantity) {
        validateValidQuantity(quantity);
        this.quantity = new BigDecimal(quantity);
    }

    private void validateValidQuantity(double quantity) {
        if (quantity <= 0) {
            throw new ValidationException(String.format("주식을 0개 이하 (%s) 로 가질 수 없습니다", quantity), ErrorCode.VALIDATION_INVALID_QUANTITY_EXCEPTION);
        }
    }

    public static Quantity of(double quantity) {
        return new Quantity(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity1 = (Quantity) o;
        return Objects.equals(quantity, quantity1.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

}
