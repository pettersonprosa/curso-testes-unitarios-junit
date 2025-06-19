package com.junit.blog.utilidade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ConversorSlugTest {

    @Test
    void deveConverterJuntoComCodigo() {
        try(MockedStatic<GeradorCodigo> mockedStatic = Mockito.mockStatic(GeradorCodigo.class)) {
            mockedStatic.when(GeradorCodigo::gerar).thenReturn("123456");

            String converterJuntoComCodigo = ConversorSlug.converterJuntoComCodigo("Olá mundo Java!");

            assertEquals("ola-mundo-java-123456", converterJuntoComCodigo);
        }

    }
}
