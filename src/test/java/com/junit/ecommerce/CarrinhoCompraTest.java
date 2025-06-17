package com.junit.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Carrinho de Compras")
class CarrinhoCompraTest {
    @Nested
    @DisplayName("Dado um carrinho vazio")
    class CarrinhoVazio {
        private CarrinhoCompra carrinho;
        private Cliente cliente;
        private Produto produto1;
        // private Produto produto2;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "João Silva");
            produto1 = new Produto(1L, "Notebook", "Notebook Gamer Asus", new BigDecimal("2500.00"));
            // produto2 = new Produto(2L, "Mouse", "Mouse Razer", new BigDecimal("50.00"));
            carrinho = new CarrinhoCompra(cliente);
        }

        @Test
        @DisplayName("Então deve retornar lista de itens vazia")
        void deveRetornarListaVazia() {
            assertTrue(carrinho.getItens().isEmpty());
        }

        @Test
        @DisplayName("Então deve retornar valor total zero")
        void deveRetornarValorTotalZero() {
            assertEquals(BigDecimal.ZERO, carrinho.getValorTotal());
        }

        @Test
        @DisplayName("Então deve retornar quantidade total zero")
        void deveRetornarQuantidadeTotalZero() {
            assertEquals(0, carrinho.getQuantidadeTotalDeProdutos());
        }

        @Nested
        @DisplayName("Quando adicionar um produto com quantidade válida")
        class AdicionarProdutoValido {
            @Test
            @DisplayName("Então deve adicionar o produto ao carrinho")
            void deveAdicionarProdutoAoCarrinho() {
                carrinho.adicionarProduto(produto1, 2);

                assertEquals(1, carrinho.getItens().size());
                assertEquals(produto1, carrinho.getItens().get(0).getProduto());
                assertEquals(2, carrinho.getItens().get(0).getQuantidade());
            }

            @Test
            @DisplayName("Então deve calcular valor total corretamente")
            void deveCalcularValorTotalCorretamente() {
                carrinho.adicionarProduto(produto1, 2);

                assertEquals(new BigDecimal("5000.00"), carrinho.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando tentar adicionar produto nulo")
        class AdicionarProdutoNulo {
            @Test
            @DisplayName("Então deve lançar NullPointerException")
            void deveLancarNullPointerException() {
                assertThrows(NullPointerException.class,
                        () -> carrinho.adicionarProduto(null, 1));
            }
        }

        @Nested
        @DisplayName("Quando tentar adicionar produto com quantidade inválida")
        class AdicionarProdutoQuantidadeInvalida {
            @Test
            @DisplayName("Então deve lançar IllegalArgumentException para quantidade zero")
            void deveLancarExceptionQuantidadeZero() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinho.adicionarProduto(produto1, 0));
            }

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException para quantidade negativa")
            void deveLancarExceptionQuantidadeNegativa() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinho.adicionarProduto(produto1, -1));
            }
        }

        @Nested
        @DisplayName("Quando tentar remover produto inexistente")
        class RemoverProdutoInexistente {
            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinho.removerProduto(produto1));
            }
        }

        @Nested
        @DisplayName("Quando tentar aumentar quantidade de produto inexistente")
        class AumentarQuantidadeProdutoInexistente {
            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinho.aumentarQuantidadeProduto(produto1));
            }
        }

        @Nested
        @DisplayName("Quando tentar diminuir quantidade de produto inexistente")
        class DiminuirQuantidadeProdutoInexistente {
            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException() {
                assertThrows(IllegalArgumentException.class,
                        () -> carrinho.diminuirQuantidadeProduto(produto1));
            }
        }
    }

    @Nested
    @DisplayName("Dado um carrinho com um produto")
    class CarrinhoComUmProduto {
        private CarrinhoCompra carrinho;
        private Cliente cliente;
        private Produto produto1;
        private Produto produto2;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "João Silva");
            produto1 = new Produto(1L, "Notebook", "Notebook Gamer Asus", new BigDecimal("2500.00"));
            produto2 = new Produto(2L, "Mouse", "Mouse Razer", new BigDecimal("50.00"));
            carrinho = new CarrinhoCompra(cliente);
            carrinho.adicionarProduto(produto1, 1);
        }

        @Test
        @DisplayName("Então deve retornar lista com um item")
        void deveRetornarListaComUmItem() {
            assertEquals(1, carrinho.getItens().size());
        }

        @Test
        @DisplayName("Então deve retornar quantidade total um")
        void deveRetornarQuantidadeTotalUm() {
            assertEquals(1, carrinho.getQuantidadeTotalDeProdutos());
        }

        @Nested
        @DisplayName("Quando adicionar o mesmo produto novamente")
        class AdicionarMesmoProduto {
            @Test
            @DisplayName("Então deve incrementar a quantidade")
            void deveIncrementarQuantidade() {
                carrinho.adicionarProduto(produto1, 2);

                assertEquals(1, carrinho.getItens().size());
                assertEquals(3, carrinho.getItens().get(0).getQuantidade());
            }

            @Test
            @DisplayName("Então deve atualizar valor total")
            void deveAtualizarValorTotal() {
                carrinho.adicionarProduto(produto1, 2);

                assertEquals(new BigDecimal("7500.00"), carrinho.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando adicionar produto diferente")
        class AdicionarProdutoDiferente {
            @Test
            @DisplayName("Então deve ter dois itens no carrinho")
            void deveTerDoisItensNoCarrinho() {
                carrinho.adicionarProduto(produto2, 1);

                assertEquals(2, carrinho.getItens().size());
            }

            @Test
            @DisplayName("Então deve calcular valor total de ambos produtos")
            void deveCalcularValorTotalAmbosProdutos() {
                carrinho.adicionarProduto(produto2, 1);

                assertEquals(new BigDecimal("2550.00"), carrinho.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando remover o produto")
        class RemoverProduto {
            @Test
            @DisplayName("Então deve remover o produto do carrinho")
            void deveRemoverProdutoDoCarrinho() {
                carrinho.removerProduto(produto1);

                assertTrue(carrinho.getItens().isEmpty());
            }

            @Test
            @DisplayName("Então valor total deve ser zero")
            void valorTotalDeveSerZero() {
                carrinho.removerProduto(produto1);

                assertEquals(BigDecimal.ZERO, carrinho.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando aumentar quantidade do produto")
        class AumentarQuantidadeProduto {
            @Test
            @DisplayName("Então deve incrementar quantidade em um")
            void deveIncrementarQuantidadeEmUm() {
                carrinho.aumentarQuantidadeProduto(produto1);

                assertEquals(2, carrinho.getItens().get(0).getQuantidade());
            }

            @Test
            @DisplayName("Então deve atualizar valor total")
            void deveAtualizarValorTotal() {
                carrinho.aumentarQuantidadeProduto(produto1);

                assertEquals(new BigDecimal("5000.00"), carrinho.getValorTotal());
            }
        }

        @Nested
        @DisplayName("Quando diminuir quantidade do produto")
        class DiminuirQuantidadeProduto {
            @Test
            @DisplayName("Então deve remover o produto do carrinho (quantidade = 1)")
            void deveRemoverProdutoDoCarrinho() {
                carrinho.diminuirQuantidadeProduto(produto1);

                assertTrue(carrinho.getItens().isEmpty());
            }
        }

        @Nested
        @DisplayName("Quando esvaziar o carrinho")
        class EsvaziarCarrinho {
            @Test
            @DisplayName("Então deve remover todos os itens")
            void deveRemoverTodosItens() {
                carrinho.esvaziar();

                assertTrue(carrinho.getItens().isEmpty());
            }

            @Test
            @DisplayName("Então valor total deve ser zero")
            void valorTotalDeveSerZero() {
                carrinho.esvaziar();

                assertEquals(BigDecimal.ZERO, carrinho.getValorTotal());
            }
        }
    }

    @Nested
    @DisplayName("Dado um carrinho com produto de quantidade maior que um")
    class CarrinhoComProdutoQuantidadeMaior {
        private CarrinhoCompra carrinho;
        private Cliente cliente;
        private Produto produto1;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "João Silva");
            produto1 = new Produto(1L, "Notebook", "Notebook Asus", new BigDecimal("2500.00"));
            carrinho = new CarrinhoCompra(cliente);
            carrinho.adicionarProduto(produto1, 3);
        }

        @Nested
        @DisplayName("Quando diminuir quantidade do produto")
        class DiminuirQuantidadeProduto {
            @Test
            @DisplayName("Então deve decrementar quantidade em um")
            void deveDecrementarQuantidadeEmUm() {
                carrinho.diminuirQuantidadeProduto(produto1);

                assertEquals(2, carrinho.getItens().get(0).getQuantidade());
            }

            @Test
            @DisplayName("Então deve atualizar valor total")
            void deveAtualizarValorTotal() {
                carrinho.diminuirQuantidadeProduto(produto1);

                assertEquals(new BigDecimal("5000.00"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("Então produto deve permanecer no carrinho")
            void produtoDevePermancerNoCarrinho() {
                carrinho.diminuirQuantidadeProduto(produto1);

                assertEquals(1, carrinho.getItens().size());
            }
        }
    }

    @Nested
    @DisplayName("Dado um carrinho com múltiplos produtos")
    class CarrinhoComMultiplosProdutos {
        private CarrinhoCompra carrinho;
        private Cliente cliente;
        private Produto produto1;
        private Produto produto2;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "João Silva");
            produto1 = new Produto(1L, "Notebook", "Notebook Gamer Asus", new BigDecimal("2500.00"));
            produto2 = new Produto(2L, "Mouse", "Mouse Razer", new BigDecimal("50.00"));
            carrinho = new CarrinhoCompra(cliente);
            carrinho.adicionarProduto(produto1, 2);
            carrinho.adicionarProduto(produto2, 3);
        }

        @Test
        @DisplayName("Então deve calcular quantidade total corretamente")
        void deveCalcularQuantidadeTotalCorretamente() {
            assertEquals(5, carrinho.getQuantidadeTotalDeProdutos());
        }

        @Test
        @DisplayName("Então deve calcular valor total corretamente")
        void deveCalcularValorTotalCorretamente() {
            // 2 * 2500 + 3 * 50 = 5000 + 150 = 5150
            assertEquals(new BigDecimal("5150.00"), carrinho.getValorTotal());
        }

        @Test
        @DisplayName("Então getItens deve retornar cópia da lista")
        void getItensDeveRetornarCopia() {
            List<ItemCarrinhoCompra> itens = carrinho.getItens();
            itens.clear();

            // Lista original não deve ser afetada
            assertEquals(2, carrinho.getItens().size());
        }
    }

    @Nested
    @DisplayName("Dado construção do carrinho")
    class ConstrucaoCarrinho {
        @Test
        @DisplayName("Quando cliente é nulo então deve lançar NullPointerException")
        void clienteNuloDeveLancarException() {
            assertThrows(NullPointerException.class,
                    () -> new CarrinhoCompra(null));
        }

        @Test
        @DisplayName("Quando lista de itens é nula então deve lançar NullPointerException")
        void listaItensNulaDeveLancarException() {
            Cliente cliente = new Cliente(1L, "João Silva");
            assertThrows(NullPointerException.class,
                    () -> new CarrinhoCompra(cliente, null));
        }
    }

    @Nested
    @DisplayName("Dado operações com parâmetros nulos")
    class OperacoesParametrosNulos {
        private CarrinhoCompra carrinho;
        private Cliente cliente;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "João Silva");
            carrinho = new CarrinhoCompra(cliente);
        }

        @Test
        @DisplayName("Quando remover produto nulo então deve lançar NullPointerException")
        void removerProdutoNuloDeveLancarException() {
            assertThrows(NullPointerException.class,
                    () -> carrinho.removerProduto(null));
        }

        @Test
        @DisplayName("Quando aumentar quantidade produto nulo então deve lançar NullPointerException")
        void aumentarQuantidadeProdutoNuloDeveLancarException() {
            assertThrows(NullPointerException.class,
                    () -> carrinho.aumentarQuantidadeProduto(null));
        }

        @Test
        @DisplayName("Quando diminuir quantidade produto nulo então deve lançar NullPointerException")
        void diminuirQuantidadeProdutoNuloDeveLancarException() {
            assertThrows(NullPointerException.class,
                    () -> carrinho.diminuirQuantidadeProduto(null));
        }
    }
}