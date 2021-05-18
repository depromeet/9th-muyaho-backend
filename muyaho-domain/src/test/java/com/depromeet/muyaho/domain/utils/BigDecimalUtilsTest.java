package com.depromeet.muyaho.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BigDecimalUtilsTest {

    @MethodSource("source_big_decimal_round_floor")
    @ParameterizedTest
    void roundUp_호출시_소수점_둘째자리이후로_버린다(BigDecimal value, String expect) {
        // when
        String result = BigDecimalUtils.roundFloor(value);

        // then
        assertThat(result).isEqualTo(expect);
    }

    private static Stream<Arguments> source_big_decimal_round_floor() {
        return Stream.of(
            Arguments.of(new BigDecimal("10.345"), "10.34"),
            Arguments.of(new BigDecimal("10.344"), "10.34"));
    }

    @Test
    void 소수점에_포함되어있는_0을_제거한다() {
        // given
        BigDecimal value = new BigDecimal("10.000000");

        // when
        String result = BigDecimalUtils.roundFloor(value);

        // then
        assertThat(result).isEqualTo("10");
    }

    @Test
    void 두_값을_비교해서_차이률을_계산한다_타겟보다_더_직아진경우_음수로_계산된다() {
        // given
        BigDecimal one = new BigDecimal(100);
        BigDecimal target = new BigDecimal(110);

        // when
        BigDecimal result = BigDecimalUtils.calculateDifferencePercent(one, target);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal(-10));
    }

    @Test
    void 두_값을_비교해서_차이률을_계산한다_타겟보다_더_큰경우_양수로_계산된다() {
        // given
        BigDecimal one = new BigDecimal(110);
        BigDecimal target = new BigDecimal(100);

        // when
        BigDecimal result = BigDecimalUtils.calculateDifferencePercent(one, target);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal(10));
    }

}
