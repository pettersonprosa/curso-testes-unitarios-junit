package com.junit.utilidade;

import static com.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SaudacaoUtilTest {

    @ParameterizedTest
    @ValueSource(ints = {5,6,7,8,9,10,11})
    public void Dado_horario_matinal_Quando_saudar_Entao_deve_retornar_bom_dia(int hora) {
        String saudacao = SaudacaoUtil.saudar(hora);

        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void Dado_uma_horario_matuino_Quando_saudar_Entao_deve_retornar_bom_dia() {
        int horaValida = 9;
        String saudacao = saudar(horaValida);
        // assertEquals("Bom dia", saudacao);
        String saudacaoCorreta = "Bom dia";
        
        Assertions.assertThat(saudacao)
                .as("Validando se a saudação é %s", saudacaoCorreta)
                .withFailMessage("Erro: Saudação incorreta! Resultado: %s", saudacao)
                .isEqualTo(saudacaoCorreta);
                // .withFailMessage("Saudação incorreta!") //quando usamos msg customizada ela
                // não mostra as comparações dos valores;
    }

    @Test
    public void Dado_uma_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde() {
        int horaValida = 15;
        String saudacao = saudar(horaValida);
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    public void Dado_uma_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite() {
        int horaValida = 4;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao);
    }

    @Test
    public void Dado_uma_hora_invalida_Quando_saudar_Entao_deve_lancar_exception() {
        int horaInvalida = -10;
        // Executable chamadaInvalidaDeMetodo = () -> saudar(horaInvalida);
        // IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaInvalidaDeMetodo);
        // assertEquals("Hora inválida", e.getMessage());

        Assertions.assertThatThrownBy(() -> saudar(horaInvalida))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessage("Hora inválida");
    }

    @Test
    public void Dado_uma_hora_valida_Quando_saudar_Entao_nao_deve_lancar_exception() {
        int horaValida = 0;
        Executable chamadaValidaDeMetodo = () -> saudar(horaValida);
        assertDoesNotThrow(chamadaValidaDeMetodo);
    }
}
