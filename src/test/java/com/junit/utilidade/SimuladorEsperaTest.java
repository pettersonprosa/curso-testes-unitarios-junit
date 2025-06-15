package com.junit.utilidade;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

public class SimuladorEsperaTest {

    @Test
    // @Disabled("Não é mais aplicável")
    @EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    void deveEsperarENaoDarTimeout() {
        //Assumptions.assumeTrue("PROD".equals(System.getenv("ENV")), () -> "Abortando teste: Não deve ser executado em PROD");
        
        // Este método espera a conclusão da tarefa mesmo se exceder o tempo limite
        // Assertions.assertTimeout(Duration.ofSeconds(1),
        //         () -> SimuladorEspera.esperar(Duration.ofSeconds(10)));
        
        // Este método interrompe a execução quando o tempo limite é excedido
        // Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1),
        //         () -> SimuladorEspera.esperar(Duration.ofSeconds(10)));
        
        // Verifica se a operação de espera completa dentro do tempo limite
        // Usa assertTimeoutPreemptively que aborta a execução se exceder o tempo máximo
        // Parâmetros:
        //   - Duração máxima permitida: 1 segundo
        //   - Lambda que executa a operação de espera de 100 milissegundos
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1),
                () -> SimuladorEspera.esperar(Duration.ofMillis(100)));

    }
}
