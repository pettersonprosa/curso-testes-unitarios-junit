package com.junit.utilidade;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FiltroNumerosTest {

    @Test
    public void deve_Retornar_Numeros_Pares() {
        // Cria uma lista de números inteiros para teste (1, 2, 3, 4)
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        
        // Define o resultado esperado após a filtragem (apenas números pares: 2, 4)
        List<Integer> numerosParesEsperados = Arrays.asList(2, 4);
        
        // Chama o método que está sendo testado (numerosPares) passando a lista de números
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);
        
        // Verifica se o resultado da filtragem corresponde ao esperado
        // assertIterableEquals é usado para comparar coleções (List, Set, etc.)
        Assertions.assertIterableEquals(numerosParesEsperados, resultadoFiltro);
    }

}
