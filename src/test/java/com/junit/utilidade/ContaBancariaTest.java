package com.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ContaBancariaTest {

    @Nested
    class Saque {
        @Test
        void deveRealizarSaqueComSucesso() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            conta.saque(new BigDecimal("300.00"));

            assertEquals(new BigDecimal("700.00"), conta.saldo());
        }

        @Test
        void deveLancarExcecaoQuandoSaqueValorNulo() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.saque(null));
        }

        @Test
        void deveLancarExcecaoQuandoSaqueValorZero() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.saque(BigDecimal.ZERO));
        }

        @Test
        void deveLancarExcecaoQuandoSaqueValorNegativo() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.saque(new BigDecimal("-100.00")));
        }

        
    }

    @Nested
    class Deposito {
        @Test
        void deveRealizarDepositoComSucesso() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            conta.deposito(new BigDecimal("300.00"));

            assertEquals(new BigDecimal("1300.00"), conta.saldo());
        }

        @Test
        void deveLancarExcecaoQuandoDepositoValorNulo() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.deposito(null));
        }

        @Test
        void deveLancarExcecaoQuandoDepositoValorZero() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.deposito(BigDecimal.ZERO));
        }

        @Test
        void deveLancarExcecaoQuandoDepositoValorNegativo() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("1000.00"));
            assertThrows(IllegalArgumentException.class, () -> conta.deposito(new BigDecimal("-100.00")));
        }
    }

    @Nested
    class Saldo {
        @Test
        void deveCriarContaComSaldo() {
            BigDecimal saldoInicial = new BigDecimal("1000.00");
            ContaBancaria conta = new ContaBancaria(saldoInicial);

            assertEquals(saldoInicial, conta.saldo());
        }

        @Test
        void deveLancarExcecaoQuandoSaldoInicialForNulo() {
            assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));
        }

        @Test
        void devePermitirSaldoInicialZero() {
            assertDoesNotThrow(() -> new ContaBancaria(BigDecimal.ZERO));
        }

        @Test
        void devePermitirSaldoInicialNegativo() {
            assertDoesNotThrow(() -> new ContaBancaria(new BigDecimal("-100.00")));
        }

        @Test
        void deveRetornarSaldoCorretamente() {
            BigDecimal saldoInicial = new BigDecimal("1500.00");
            ContaBancaria conta = new ContaBancaria(saldoInicial);

            BigDecimal saldoRetornado = conta.saldo();

            assertEquals(0, saldoInicial.compareTo(saldoRetornado),
                    "O saldo retornado deve ser igual ao saldo inicial");
        }

        @Test
        void deveLancarExcecaoQuandoSaldoInsuficiente() {
            ContaBancaria conta = new ContaBancaria(new BigDecimal("100.00"));
            assertThrows(RuntimeException.class, () -> conta.saque(new BigDecimal("200.00")));
        }
    }

}
