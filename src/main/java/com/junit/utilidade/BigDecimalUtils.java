package com.junit.utilidade;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static boolean iguais(BigDecimal x, BigDecimal y) {
        return x.compareTo(y) == 0;
    }

}
