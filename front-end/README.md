# Arquitetura do Projeto

Este projeto Angular foi construido de forma modular, visando escalabilidade, reutilização de código e facilidade de manutenção. 

## Estrutura de Pastas
A estrutura do projeto está dividida em três diretórios principais: core, shared, e features. Cada um desses diretórios tem uma responsabilidade bem definida, conforme descrito abaixo:

### Core
O diretório core contém elementos centrais que são utilizados em todo o aplicativo:

constants: Armazena valores constantes que são usados em várias partes do aplicativo. Esses valores são centralizados aqui para garantir consistência e facilitar a manutenção.

models: Contém as definições dos modelos de dados utilizados no projeto. Esses modelos representam as entidades de negócio e são compartilhados entre diferentes funcionalidades.

services: Este diretório concentra todos os serviços que fornecem funcionalidades reutilizáveis e comunicação com APIs.

utils: Armazena funções utilitárias, que são operações comuns e repetitivas, promovendo a reutilização de código por todo o projeto.

### Features
O diretório features é responsável por armazenar as funcionalidades específicas do aplicativo. 

abertura-conta: Funcionalidade para abertura de contas.

acessar-conta: Funcionalidade para acesso à conta.

consultar-contas: Funcionalidade para consulta de contas.

Cada uma delas é isolada, facilitando o desenvolvimento e a manutenção, permitindo uma fácil extensão do projeto.

### Shared
O diretório shared contém elementos que são compartilhados entre diferentes partes do aplicativo:

components: Contém componentes reutilizáveis, como breadcrumb e navbar. Esses componentes são modulares e podem ser utilizados em diferentes módulos do projeto sem a necessidade de duplicação de código.

shared-components.module.ts: Módulo que organiza e exporta os componentes compartilhados, tornando-os disponíveis para uso em outras partes do projeto.

Conclusão
Esta organização modular permite que o projeto Angular seja escalável e de fácil manutenção. Cada parte do projeto é cuidadosamente separada de acordo com suas responsabilidades.