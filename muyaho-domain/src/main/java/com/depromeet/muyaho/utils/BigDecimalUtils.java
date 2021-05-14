package com.depromeet.muyaho.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtils {

    public static String roundFloor(BigDecimal number) {
        return number.setScale(2, RoundingMode.FLOOR).stripTrailingZeros().toPlainString();
    }

    public static BigDecimal calculateDifferencePercent(BigDecimal benchMark, BigDecimal target) {
        if (benchMark.compareTo(target) >= 0) {
            return benchMark.subtract(target)
                .divide(target, new MathContext(2))
                .multiply(new BigDecimal(100));
        }
        return target.subtract(benchMark)
            .divide(benchMark, new MathContext(2))
            .multiply(new BigDecimal(-100));
    }

}
