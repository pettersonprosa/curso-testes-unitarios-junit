package com.junit.blog.utilidade;

public class GeradorCodigo {

    private GeradorCodigo() {

    }

    public static String gerar() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
