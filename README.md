# Intruções iniciais para a execução dos Projetos Angular e Spring Boot

### Pré-requisitos
Antes de começar, certifique-se de que você tem os seguintes softwares instalados em sua máquina:
- Node.js 18.x ou superior - (Recomendado o uso de NVM -> [Guia Instalação](https://www.treinaweb.com.br/blog/instalando-e-gerenciando-varias-versoes-do-node-js-com-nvm) 
- Java 11 - (Recomendado o uso de SDKMAN -> [Guia Linux](https://sdkman.io/) ou [Guia Windows](https://www.youtube.com/watch?v=hFiFQcfT9U0))
- Maven - (Recomendado o uso de SDKMAN -> [Guia Linux](https://sdkman.io/) ou [Guia Windows](https://www.youtube.com/watch?v=hFiFQcfT9U0))
- [Spring Tool Suite 4](https://spring.io/tools/) ou [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download)
- [Postman](https://www.postman.com/downloads/) (Opcional)

# Angular 
### Instalando Dependências
Após clonar o repositório rode o seguinte comando:
`cd front-end/ && npm i`

### Iniciar o Servidor de Desenvolvimento
`npm start`
O projeto Angular estará disponível em http://localhost:4200

# Java 
### Instalando Dependências
Após clonar o repositório rode o seguinte comando:
`cd back-end/ && mvn clean install`

### Iniciar o Servidor de Desenvolvimento
`mvn spring-boot:run`
O projeto Angular estará disponível em http://localhost:8080

# Postman (Opcional)
Caso desejem realizar testes via Postman, pode ser feito a importação da coleção presente na pasta **api-docs**.
