package com.cstor.util;

import java.util.Locale;

/**
 * Created on 2016/6/15
 *
 * @author feng.wei
 */
public class StringUtils {


    /**
     * The same as String.format(Locale.ENGLISH, format, objects).
     */
    public static String format(final String format, final Object... objects) {
        return String.format(Locale.ENGLISH, format, objects);
    }

    /**
     * Format a percentage for presentation to the user.
     *
     * @param fraction      the percentage as a fraction, e.g. 0.1 = 10%
     * @param decimalPlaces the number of decimal places
     * @return a string representation of the percentage
     */
    public static String formatPercent(double fraction, int decimalPlaces) {
        return format("%." + decimalPlaces + "f%%", fraction * 100);
    }

}
