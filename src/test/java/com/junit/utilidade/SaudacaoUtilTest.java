package com.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes no utilitário de saudação")
public class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saldar com bom dia")
    public void saudarComBomDia() {
        int horaValida = 7;
        String saudacao = SaudacaoUtil.saudar(horaValida);

        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    @DisplayName("Deve saldar com bom dia às 5 horas")
    public void saudarComBomDiaAPartir5h() {
        int horaValida = 5;
        String saudacao = SaudacaoUtil.saudar(horaValida);

        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaTarde() {
        int horaValida = 15;
        String saudacao = SaudacaoUtil.saudar(horaValida);

        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaNoite() {
        int horaValida = 20;
        String saudacao = SaudacaoUtil.saudar(horaValida);

        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaNoiteAs4h() {
        int horaValida = 4;
        String saudacao = SaudacaoUtil.saudar(horaValida);

        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
    public void deveLancarException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> SaudacaoUtil.saudar(-10));
        
        assertEquals("Hora inválida", illegalArgumentException.getMessage());
    }

    @Test
    public void naoDeveLancarException() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
    }
}
