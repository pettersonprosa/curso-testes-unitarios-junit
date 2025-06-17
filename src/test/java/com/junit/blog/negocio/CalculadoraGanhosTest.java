package com.junit.blog.negocio;

import com.junit.blog.modelo.Editor;
import com.junit.blog.modelo.Ganhos;
import com.junit.blog.modelo.Post;
import com.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadoraGanhos;
    Editor autor;
    Post post;

    @BeforeAll
    static void beforeAll() {
        calculadoraGanhos = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
    }

    @BeforeEach
    void beforeEach() {
        autor = new Editor(1L, "Joao", "joao@email.com", new BigDecimal(5), true);

        post = new Post(1L, "Ecossistema Java", "O ecossistema do Java é muito maduro", autor,
                "ecossistema-java-abc", null, false, false);
    }

    @Test
    public void deveCalcularGanhos() {
        Ganhos ganhos = calculadoraGanhos.calcular(post);

        assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void deveCalcularGanhosSemPremium() {
        autor.setPremium(false);

        Ganhos ganhos = calculadoraGanhos.calcular(post);

        assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }
}