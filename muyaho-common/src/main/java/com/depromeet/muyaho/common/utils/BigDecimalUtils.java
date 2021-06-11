package com.depromeet.muyaho.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtils {

    /**
     * BigDecimal 타입의 숫자를 소수점 두자리에서 내림하는 유틸성 메소드
     */
    public static String roundFloor(BigDecimal number) {
        return number.setScale(2, RoundingMode.FLOOR).stripTrailingZeros().toPlainString();
    }

    /**
     * 두 개의 BigDecimal 간의 차이를 퍼센트로 계산하는 유틸성 메소드
     */
    public static BigDecimal calculateDifferencePercent(BigDecimal benchMark, BigDecimal target) {
        if (target.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return benchMark.subtract(target)
            .divide(target, new MathContext(2))
            .multiply(new BigDecimal(100));
    }

}
