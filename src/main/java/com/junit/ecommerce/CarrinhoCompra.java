package com.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		// Retorna uma nova lista para que a antiga não seja alterada
		return new ArrayList<>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {
		// Parâmetros não podem ser nulos, deve retornar uma exception
		Objects.requireNonNull(produto, "Produto não pode ser nulo");

		// Quantidade não pode ser menor que 1
		if (quantidade < 1) {
			throw new IllegalArgumentException("Quantidade deve ser maior que zero");
		}

		// Deve incrementar a quantidade caso o produto já exista
		Optional<ItemCarrinhoCompra> itemExistente = buscarItemPorProduto(produto);
		if (itemExistente.isPresent()) {
			itemExistente.get().adicionarQuantidade(quantidade);
		} else {
			itens.add(new ItemCarrinhoCompra(produto, quantidade));
		}
	}

	public void removerProduto(Produto produto) {
		// Parâmetro não pode ser nulo, deve retornar uma exception
		Objects.requireNonNull(produto, "Produto não pode ser nulo");

		// Caso o produto não exista, deve retornar uma exception
		Optional<ItemCarrinhoCompra> itemExistente = buscarItemPorProduto(produto);
		if (itemExistente.isEmpty()) {
			throw new IllegalArgumentException("Produto não encontrado no carrinho");
		}

		// Deve remover o produto independente da quantidade
		itens.remove(itemExistente.get());
	}

	public void aumentarQuantidadeProduto(Produto produto) {
		// Parâmetro não pode ser nulo, deve retornar uma exception
		Objects.requireNonNull(produto, "Produto não pode ser nulo");

		// Caso o produto não exista, deve retornar uma exception
		Optional<ItemCarrinhoCompra> itemExistente = buscarItemPorProduto(produto);
		if (itemExistente.isEmpty()) {
			throw new IllegalArgumentException("Produto não encontrado no carrinho");
		}

		// Deve aumentar em um quantidade do produto
		itemExistente.get().adicionarQuantidade(1);
	}

    public void diminuirQuantidadeProduto(Produto produto) {
		// Parâmetro não pode ser nulo, deve retornar uma exception
		Objects.requireNonNull(produto, "Produto não pode ser nulo");

		// Caso o produto não exista, deve retornar uma exception
		Optional<ItemCarrinhoCompra> itemExistente = buscarItemPorProduto(produto);
		if (itemExistente.isEmpty()) {
			throw new IllegalArgumentException("Produto não encontrado no carrinho");
		}

		// Deve diminuir em um quantidade do produto, caso tenha apenas um produto, deve remover da lista
		ItemCarrinhoCompra item = itemExistente.get();
		if (item.getQuantidade() == 1) {
			itens.remove(item);
		} else {
			item.subtrairQuantidade(1);
		}
	}

    public BigDecimal getValorTotal() {
		// Implementar soma do valor total de todos itens
		return itens.stream()
				.map(ItemCarrinhoCompra::getValorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public int getQuantidadeTotalDeProdutos() {
		// Retorna quantidade total de itens no carrinho
		// Exemplo em um carrinho com 2 itens, com a quantidade 2 e 3 para cada item respectivamente, deve retornar 5
		return itens.stream()
				.mapToInt(ItemCarrinhoCompra::getQuantidade)
				.sum();
	}

	public void esvaziar() {
		// Deve remover todos os itens
		itens.clear();
	}

	private Optional<ItemCarrinhoCompra> buscarItemPorProduto(Produto produto) {
		return itens.stream()
				.filter(item -> item.getProduto().equals(produto))
				.findFirst();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}