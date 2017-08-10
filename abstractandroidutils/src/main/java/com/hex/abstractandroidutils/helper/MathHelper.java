package com.hex.abstractandroidutils.helper;

import java.math.BigDecimal;

/**
 * Created by alireza on 7/5/17.
 */

public class MathHelper {

    public static BigDecimal truncateDecimal(float x, int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }

}
