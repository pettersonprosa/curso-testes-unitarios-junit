package com.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PessoaTest {

    @Test
    void assercaoAgrupada() {
        Pessoa pessoa = new Pessoa("Joao", "Silva");

        assertAll("Asserções de pessoa",
            ()-> assertEquals("Joao", pessoa.getNome()),
            ()-> assertEquals("Silva", pessoa.getSobrenome())
        );
    }
}
