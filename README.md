# Curso Testes Unitários com JUnit

Este projeto faz parte do curso *Testes Unitários com JUnit* da AlgaWorks.

Ele reúne várias miniaplicações em um único projeto, pois diferentes cenários serão abordados ao longo do curso.

# Anotações

## Pirâmide de Testes

A pirâmide de testes é um conceito na engenharia de software que representa a proporção ideal de diferentes tipos de testes em um sistema. Ela foi popularizada por Mike Cohn e ajuda a garantir qualidade no desenvolvimento de software com uma abordagem eficiente de testes automatizados.

### Estrutura da Pirâmide de Testes:
A pirâmide é dividida em três camadas principais, do mais frequente e barato ao mais raro e caro:

1. Testes Unitários (Base da pirâmide)
    - São os mais rápidos e baratos.
    - Testam unidades isoladas do código, como funções e classes.
    - Utilizam frameworks como JUnit, Mockito, AssertJ.
2. Testes de Integração (Meio da pirâmide)
    - Validam a comunicação entre componentes, como chamadas a APIs, bancos de dados ou microservices.
    - São mais lentos e mais caros do que os unitários.
    - Ferramentas comuns: Testcontainers, Spring Test, RestAssured.
3. Testes de Interface Gráfica (Topo da pirâmide)
    Simulam o comportamento do usuário na interface.
    São os mais lentos e custosos, pois exigem interações reais ou simuladas com o sistema.
    Ferramentas: Selenium, Cypress, Appium.

### Boa Prática: Testar mais na base da pirâmide
- O ideal é que a maioria dos testes sejam unitários.
- Os testes de integração devem validar a comunicação entre módulos, mas sem exagero.
- Os testes de UI devem ser poucos e bem planejados, para evitar lentidão e fragilidade.
- Exemplo de distribuição ideal:
    - 70% de testes unitários
    - 20% de testes de integração
    - 10% de testes de UI
- Observação: Algumas variações modernas incluem a pirâmide invertida (muito teste de UI) e o diamante de testes (valorizando mais testes de integração). Mas, na prática, o modelo clássico ainda é o mais eficiente.

## Princípio FIRST
O princípio FIRST é um conjunto de boas práticas criado por *Robert C. Martin (Uncle Bob)* para garantir a qualidade dos testes automatizados, especialmente os testes de unidade.

Cada letra da sigla FIRST representa uma característica essencial que um bom teste deve ter:

> Rapidez (**Fast**): os testes devem ser rápidos. Devem executar com rapidez. Quando os testes rodam devagar, você não desejará executá-los com frequência. E, consequentemente, não encontrará problemas cedo o bastante para consertá-los facilmente. E você não se sentirá livre para limpar o código, que acabará se degradando.  
>  
> Independência (**Independent**): os testes não devem depender uns dos outros. Um teste não deve configurar as condições para o próximo. Você deve ser capaz de executar cada teste de forma independente e na ordem que desejar. Quando eles dependem uns dos outros, se o primeiro falhar causará um efeito dominó de falhas, dificultando o diagnóstico e ocultando os defeitos abaixo dele.  
>  
> Repetitividade (**Repeatable**): deve-se poder repetir os testes em qualquer ambiente. Você deve ser capaz de efetuar testes no ambiente de produção, no de garantia de qualidade e no seu notebook enquanto volta para casa de trem sem uma rede disponível. Caso seus testes não possam ser repetidos em qualquer ambiente, então você sempre terá uma desculpa para o motivo das falhas. E também perceberá que não consegue rodar os testes fora o ambiente adequado.  
>  
> Autovalidação (**Self-Validating**): os testes devem ter uma saída booleana. Obtenham ou não êxito, você não deve ler um arquivo de registro para saber o resultado. Você não deve ter de comparar manualmente dois arquivos texto para ver se os testes foram bem sucedidos. Se os testes não possuírem autovalidação, então uma falha pode se tornar subjetiva, e executar os testes pode exigir uma longa validação manual.  
>  
> Pontualidade (**Timely**): os testes precisam ser escritos em tempo hábil. Devem-se criar os testes de unidade imediatamente antes do código de produção no qual serão aplicados. Se criá-los depois, o teste do código de produção poderá ficar mais difícil. Ou talvez você ache que um pouco do código de produção seja complexo demais para testar. Ou talvez você não crie o código de produção de maneira que possa ser testado.  
>
> — Robert C. Martin, *Código Limpo: Habilidades Práticas do Agile Software*, 2011.

## Padrão Triple A

O padrão Triple A (ou AAA) é uma abordagem comum para escrever testes unitários. Ele divide o teste em três etapas claras:

1. Arrange: Preparar o ambiente necessário para o teste, como criar objetos, configurar mocks e definir entradas.
2. Act: Executar a ação ou método que está sendo testado.
3. Assert: Verificar se o resultado obtido é o esperado.

Abaixo está um exemplo de um teste unitário usando o padrão Triple A:

Suponha que temos uma classe Calculadora com um método somar que queremos testar:

```java
public class Calculadora {
    public int somar(int a, int b) {
        return a + b;
    }
}
```
Agora, vamos escrever um teste unitário para o método somar usando o padrão Triple A:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class CalculadoraTest {
    @Test
    public void testSomar() {
        
        // Arrange: Preparar o ambiente
        Calculadora calculadora = new Calculadora();
        int a = 5;
        int b = 10;
        int resultadoEsperado = 15;
        
        // Act: Executar a ação
        int resultadoObtido = calculadora.somar(a, b);
        
        // Assert: Verificar o resultado
        assertEquals(resultadoEsperado, resultadoObtido, "A soma de 5 e 10 deve ser 15");
    }
}
```