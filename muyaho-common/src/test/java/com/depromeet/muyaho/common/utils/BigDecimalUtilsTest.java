package com.depromeet.muyaho.common.utils;

import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("시드가 100원인데, 110원이 되면, 수익률이 +10%가 되어야한다.")
    @Test
    void 수익률_계산로직_테스트() {
        // given
        BigDecimal finalAsset = new BigDecimal(110);
        BigDecimal seed = new BigDecimal(100);

        // when
        BigDecimal result = BigDecimalUtils.calculateDifferencePercent(finalAsset, seed);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal(10));
    }

    @DisplayName("시드가 100원인데, 90원이 되면, 수익률이 -10%가 되어야한다 ")
    @Test
    void 손실률_계산로직_테스트() {
        // given
        BigDecimal finalAsset = new BigDecimal(90);
        BigDecimal seed = new BigDecimal(100);

        // when
        BigDecimal result = BigDecimalUtils.calculateDifferencePercent(finalAsset, seed);

        // then
        assertThat(result).isEqualByComparingTo(new BigDecimal(-10));
    }

}
