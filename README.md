# Curso Testes Unitários com JUnit
Curso Testes Unitários com JUnit (AlgaWorks)

# Curso Testes Unitários com JUnit

# Anotações

## Pirâmede de Testes

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
