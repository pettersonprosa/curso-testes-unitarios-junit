package com.junit.utilidade;

import java.math.BigDecimal;

public class ContaBancaria {

    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldo) {
        if (saldo == null) {
            throw new IllegalArgumentException("Saldo não pode ser nulo");
        }
        this.saldo = saldo;
    }

    public void saque(BigDecimal valor) {
        validarValorTransacao(valor);
        if (valor.compareTo(saldo) > 0) {
            throw new RuntimeException("Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);
    }

    public void deposito(BigDecimal valor) {
        validarValorTransacao(valor);
        saldo = saldo.add(valor);
    }

    public BigDecimal saldo() {
        return saldo;
    }

    private void validarValorTransacao(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }
}
