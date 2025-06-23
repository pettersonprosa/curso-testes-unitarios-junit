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

## BDD (Behavior-Driven Development)

**Behavior-Driven Development (BDD)**, ou Desenvolvimento Orientado a Comportamento, é uma metodologia de desenvolvimento ágil de software que foi originalmente nomeada em 2003 por Dan North como uma resposta ao desenvolvimento orientado a testes (TDD).

### Definição Principal

BDD envolve nomear testes de software usando linguagem de domínio para descrever o comportamento do código, utilizando uma linguagem específica de domínio (DSL) com construções em linguagem natural (como sentenças similares ao inglês) que podem expressar o comportamento e os resultados esperados.

### Características Fundamentais

**Colaboração entre equipes:** BDD é uma técnica de desenvolvimento ágil que encoraja a colaboração entre desenvolvedores, QA e participantes não-técnicos ou de negócio em um projeto de software.

**Metodologia "outside-in":** BDD é uma metodologia "de fora para dentro" que começa identificando resultados de negócio e depois detalha o conjunto de funcionalidades que alcançarão esses resultados.

**Processo de desenvolvimento:** BDD é um processo de engenharia de software que deriva do Desenvolvimento Orientado a Testes (TDD) e do Desenvolvimento Orientado a Testes de Aceitação (ATDD).

### Objetivo Principal

O BDD foi criado para resolver confusões comuns no TDD, ajudando programadores a saber por onde começar, o que testar, quanto testar de uma vez, como nomear seus testes e como entender por que um teste falha. Essencialmente, é "uma metodologia para desenvolver software através de comunicação contínua baseada em exemplos entre desenvolvedores, QAs e analistas de negócio".

### Como funciona o BDD?
- Escreve-se especificações em linguagem natural, focadas no comportamento desejado.
- Essas especificações são automaticamente convertidas em testes que validam se o sistema se comporta conforme o esperado.
- Promove colaboração entre desenvolvedores, testers e stakeholders, alinhando expectativas e requisitos.

### Estrutura do BDD

A estrutura do BDD geralmente segue um formato padronizado chamado **Gherkin**, que consiste em três principais componentes:

| Elemento | Propósito | Exemplo |
|---|---|---|
| **Feature** | Descreve a funcionalidade geral do sistema ou recurso | `Feature: Login de usuário` |
| **Scenario** | Situação ou caso de uso específico | `Scenario: Login bem-sucedido` |
| **Steps** | Descrevem ações ou condições específicas, usando três tipos principais: |  |
| **Given** | Condição inicial ou pré-requisito | `Given que estou na página de login` |
| **When** | Ação do usuário ou evento | `When clico em "Entrar"` |
| **Then** | Resultado esperado | `Then sou redirecionado à página inicial` |

Essa estrutura ajuda a criar testes automáticos claros e fáceis de entender, que refletem o comportamento desejado do sistema.

### Exemplo completo e prático de BDD

```gherkin
Feature: Login de usuário
  As a (Como) usuário do sistema,
  I Want (Quero) fazer login,
  So that (Para) acessar minhas informações pessoais e funcionalidades protegidas.

  Scenario: Login bem-sucedido com credenciais corretas
    Given que estou na página de login
    And tenho uma conta registrada com o e-mail "usuario@example.com" e senha "senha123"
    When insiro o e-mail "usuario@example.com"
    And insiro a senha "senha123"
    And clico no botão "Entrar"
    Then devo ser direcionado para a minha página inicial
    And devo ver uma mensagem de boas-vindas

  Scenario: Falha no login com credenciais incorretas
    Given que estou na página de login
    When insiro o e-mail "usuario@example.com"
    And insiro a senha "senhaerrada"
    And clico no botão "Entrar"
    Then devo permanecer na página de login
    And devo ver uma mensagem de erro indicando credenciais inválidas
```

## Stub, Mock e Spy em Testes (Java)

Em testes de software, especialmente no desenvolvimento orientado a testes (TDD) e testes unitários, frequentemente precisamos isolar o código sob teste de suas dependências externas (como bancos de dados, APIs ou serviços). Para isso, utilizamos objetos de teste que simulam comportamentos controlados.

Os três principais tipos são:
- Stub: Fornece respostas pré-definidas para chamadas de métodos, sem preocupação com interações.
- Mock: Além de simular comportamentos, verifica se métodos foram chamados conforme o esperado.
- Spy: Permite a execução real de um objeto, mas com a capacidade de substituir (mockar) ou rastrear chamadas específicas.

### Stub

**O que é**: Um Stub é um objeto simplificado que fornece respostas pré-programadas para chamadas de método durante os testes.

**Como funciona**:
- Substitui um objeto real por uma implementação simplificada
- Retorna valores pré-definidos quando seus métodos são chamados
- Não verifica como/interações com o objeto

**Quando usar**: Quando você precisa isolar seu código de dependências externas e controlar os valores retornados por essas dependências.

**Exemplo em Java (Mockito)**:
```java
UserRepository userStub = mock(UserRepository.class);
when(userStub.findById(anyLong())).thenReturn(new User("John Doe"));

UserService service = new UserService(userStub);
User user = service.getUser(1L); // Sempre retornará John Doe
```

### Mock

**O que é**: Um Mock é um objeto que simula o comportamento de um objeto real e pode verificar como ele foi interagido.

**Como funciona**:
- Registra chamadas de método
- Pode retornar valores pré-definidos como um Stub
- Verifica se os métodos foram chamados, com quais parâmetros e quantas vezes

**Quando usar**: Quando você precisa verificar as interações entre seu código e suas dependências.

**Exemplo em Java (Mockito)**:
```java
EmailService emailMock = mock(EmailService.class);
UserService service = new UserService(emailMock);

service.sendWelcomeEmail("user@test.com");

verify(emailMock).sendEmail("user@test.com", "Welcome!");
```

### Spy

**O que é**: Um Spy é um objeto que envolve um objeto real, permitindo que você substitua alguns métodos enquanto mantém o comportamento original para outros.

**Como funciona**:
- Chama os métodos reais por padrão
- Permite substituir (stub) métodos específicos
- Pode verificar interações como um Mock

**Quando usar**: Quando você quer testar a maioria do comportamento real de um objeto, mas precisa substituir ou verificar apenas algumas partes.

**Exemplo em Java (Mockito)**:
```java
List<String> realList = new ArrayList<>();
List<String> spyList = spy(realList);

// Substitui um método específico
when(spyList.size()).thenReturn(100);

// Outros métodos funcionam normalmente
spyList.add("one");
spyList.add("two");

assertEquals(2, spyList.size()); // Retorna 100 por causa do stub
assertTrue(spyList.contains("one")); // Comportamento real
```

### Diferenças Principais

| Característica | Stub               | Mock               | Spy                |
|----------------|--------------------|--------------------|--------------------|
| Objetivo       | Fornecer respostas | Verificar interações | Observar objeto real |
| Comportamento  | Totalmente controlado | Totalmente controlado | Real por padrão, substituível |
| Verificações   | Não faz verificações | Verifica chamadas | Pode verificar chamadas |
| Uso típico     | Isolar código      | Testar interações  | Testar parcialmente objetos reais |

## Object Mother Pattern

O **Object Mother** é um padrão de design (design pattern) usado em testes de software para facilitar a criação de objetos de teste com dados consistentes e válidos.

### O que é o Object Mother?

É um padrão que encapsula a lógica de criação de objetos de teste em métodos factory, permitindo:

- Criar instâncias de objetos com dados padrão para testes
- Reduzir a duplicação de código nos testes
- Manter a consistência dos dados de teste
- Facilitar a manutenção quando a estrutura das classes muda

### Como funciona

1. Uma classe "Object Mother" contém métodos estáticos que retornam objetos configurados
2. Cada método cria um objeto em um estado específico (válido, inválido, edge case)
3. Os testes chamam esses métodos em vez de instanciar objetos manualmente

### Exemplo em Java

```java
public class CustomerObjectMother {
    
    public static Customer createValidCustomer() {
        return new Customer("John Doe", "john@example.com", "123-456-7890");
    }
    
    public static Customer createCustomerWithInvalidEmail() {
        return new Customer("John Doe", "invalid-email", "123-456-7890");
    }
    
    public static Customer createPremiumCustomer() {
        Customer customer = new Customer("Premium User", "premium@example.com", "987-654-3210");
        customer.setPremium(true);
        return customer;
    }
}
```

### Uso nos testes

```java
@Test
public void testValidCustomer() {
    Customer customer = CustomerObjectMother.createValidCustomer();
    assertTrue(customer.isValid());
}

@Test
public void testPremiumDiscount() {
    Customer customer = CustomerObjectMother.createPremiumCustomer();
    assertEquals(0.9, customer.getDiscountRate(), 0.01);
}
```

### Vantagens

- **Consistência**: Todos os testes usam os mesmos dados de teste
- **Manutenção**: Mudanças nos construtores afetam apenas a Object Mother
- **Legibilidade**: Testes ficam mais claros e focados no comportamento
- **Produtividade**: Reduz o tempo para escrever novos testes

### Quando usar

- Quando há muitos testes que criam os mesmos objetos repetidamente  
- Quando a criação dos objetos apresenta alta complexidade  
- Quando é necessário garantir consistência nos dados de teste  
- Quando os objetos de domínio possuem múltiplas combinações de estado válido  

## Testes Parametrizados

Os testes parametrizados são uma técnica de automação de testes que permite executar o mesmo teste com diferentes conjuntos de dados de entrada, evitando duplicação de código e tornando os testes mais eficientes e abrangentes. 

### Como funcionam

O funcionamento básico envolve três componentes principais:

**Definição dos parâmetros**: Você especifica os diferentes valores que serão usados como entrada para o teste. Isso pode ser feito através de anotações, arquivos externos, ou métodos que retornam coleções de dados.

**Método de teste**: Um único método que recebe os parâmetros como argumentos e executa a lógica de teste usando esses valores.

**Execução**: O framework de teste automaticamente executa o método para cada conjunto de parâmetros, tratando cada execução como um teste individual.

### Exemplos práticos

Em **JUnit 5** (Java):
```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 5, 8})
void testEhPositivo(int numero) {
    assertTrue(numero > 0);
}

@ParameterizedTest
@CsvSource({
    "1, 1, 2",
    "2, 3, 5", 
    "5, 8, 13"
})
void testSoma(int a, int b, int esperado) {
    assertEquals(esperado, a + b);
}
```

### Vantagens

**Redução de duplicação**: Elimina a necessidade de escrever múltiplos métodos de teste similares.

**Cobertura ampliada**: Facilita testar mais cenários e casos extremos sem aumentar significativamente o código.

**Manutenibilidade**: Mudanças na lógica de teste precisam ser feitas em apenas um lugar.

**Legibilidade**: Os dados de teste ficam claramente separados da lógica, tornando mais fácil entender quais cenários estão sendo testados.

**Relatórios detalhados**: Cada execução é reportada individualmente, facilitando identificar exatamente qual conjunto de dados causou uma falha.

Os testes parametrizados são especialmente úteis para validação de entrada, testes de cálculos matemáticos, verificação de regras de negócio com diferentes condições, e qualquer situação onde é necessário verificar o mesmo comportamento com dados variados.