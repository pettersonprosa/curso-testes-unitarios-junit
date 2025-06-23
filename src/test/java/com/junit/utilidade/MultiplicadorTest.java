package com.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MultiplicadorTest {

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class, names = {"DOBRO", "TRIPLO"})
    void testAplicarMultiplicador(Multiplicador multiplicador) {
        assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }

    @ParameterizedTest
    @EnumSource(value = Multiplicador.class)
    void testAplicarMultiplicadorTodos(Multiplicador multiplicador) {
        assertNotNull(multiplicador.aplicarMultiplicador(10.0));
    }

}
