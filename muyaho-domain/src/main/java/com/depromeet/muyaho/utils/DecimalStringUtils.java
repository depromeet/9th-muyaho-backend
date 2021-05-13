package com.depromeet.muyaho.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DecimalStringUtils {

    public static String covertToString(double number) {
        final DecimalFormat format = new DecimalFormat("#.##");
        return format.format(number);
    }

}
