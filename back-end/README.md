# Padrões de Projeto Aplicados no Projeto

## 1. Padrão MVC (Model-View-Controller)

### Model:
O pacote entity contém todas nossas classes que representam o modelo do domínio. Essas classes mapeiam as tabelas do banco de dados e são usadas para persistência.

### View:
Em uma API REST, a "View" diferentemente do modelo tradicional, será a camada que irá retornar as respostas HTTP em formato JSON.

### Controller:
O pacote controller abriga as classes que irão expor os endpoints da API. Eles atuam como intermediários, recebendo as solicitações HTTP, invocando a lógica de negócios e retornando a resposta adequada.


## 2. Padrão DAO (Data Access Object)

As classes ContaRepository, TitularRepository e TransferenciaRepository no pacote repository seguem o padrão DAO. Elas abstraem a interação com o banco de dados, oferecendo métodos específicos para operações CRUD e consultas customizadas. 


## 3. Padrão DTO (Data Transfer Object)

O uso de Data Transfer Objects (DTOs) no projeto, facilita a transferência de dados entre a camada de apresentação (controllers) e a camada de serviço. Eles ajudam a evitar expor diretamente as entidades do banco de dados, promovendo maior segurança.


## 4. Padrão Service Layer

Essas classes encapsulam a lógica de negócios, coordenam as operações entre diferentes repositórios e gerenciam transações. Elas se comunicam diretamente com os controladores e os repositórios.


## 5. Padrão Exception Handling

Atraves de uma classe Controladora que faz uso da anotação @ControllerAdvice, ao propagar exceções como por exemplo: NotFoundException e BusinessException, as mesmas serão interceptadas e tratadas de maneira centralizada.


# Fluxo Resumido

## Controller:
Um cliente faz uma solicitação de transferência por meio de um endpoint declarado em ContaController.

## Service Layer:
O ContaService é invocado pelo controlador. Ele coordena as operações, como verificar saldo, calcular taxas e persistir a transferência.

## DAO/Repository:
ContaRepository, TitularRepository e TransferenciaRepository são usados para consultar e persistir dados.

## DTO:
Os dados retornados são manipulados usando DTOs para transportar informações entre o controlador e o serviço, garantindo que as informações retornadas sejam mais adequadas para o cliente.

## Exception Handling:
Se algum erro ocorrer (por exemplo, saldo insuficiente), uma exceção personalizada é lançada e tratada, com uma resposta apropriada sendo enviada ao cliente.