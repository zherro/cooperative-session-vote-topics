# Cooperative API

Projeto destinado a avalição de habilidades de codificação e implementação de soluções.

Objetivo: criar api para votação de pautas

### Demo

- [Cooperative API](https://cooperative-api.herokuapp.com/swagger-ui.html)

- [MVP APP](https://cooperative-mvp-app.herokuapp.com) *comtempla os principais serviços dísponiveis na api

### Sobre

O projeto foi construído utilizando:

* **Java 11**:  permite a inferência de tipos para variáveis locais em expressões lambda, o que completa o recurso de inferência de tipos para variáveis locais lançado com o Java 10;
* **SpringBoot 2.5.2**: ferramenta que visa facilitar o processo de configuração e publicação de aplicações;
* **Gradle 7**: uma ferramenta de build open source bastante poderosa, que nos permite gerenciamento de dependências e criação de build do projeto;
* **Swagger 2**: biblioteca open source que auxilia nos processos de definir, criar, documentar e consumir APIs REST;
* **Flyway Migration**: ferramenta para organização e execução de scripts SQL no banco de dados, funcionando como um controlador de versão de banco de dados também;


* **JUnit 5**:  framework que auxilia na escrita de testes unitários;
* **Mockito** framework de mocking para uso em testes de unitários. Ele possui uma API clara e simples. A curva de aprendizado é suave e ele possui uma documentação bastante útil;


* **PostgreSQL ^10:** banco de dados relacional-objeto que usa 'Structured Query Language' (SQL) além de sua própria linguagem procedural, PL / pgSQL. O PostgreSQL é fácil de usar, gratuito e com muitos recursos disponíveis.
* **RabbitMQ:** um software de mensagens com código aberto, que implementa o protocolo "Advanced Message Queuing Protocol" (AMQP).

> Todas as dependências do projeto são gerenciadas pelo Gradle, não sendo necessária nenhuma configuração adicional para execução

## Projetos auxiliares

**[USERS API](users-api/README.md)** **IMPORTANTE** *

**[COOPERATIVE APP MVP](cooperative-mvp-app/README.md)**

## Como executar o projeto ?

### DEV (execução local)

Em ambiente de dev é necessário possuir instalado na máquina o java 11 e banco de dados postgreSQL 10 ou superior.

A versão 3 deste projeto utiliza RabbitMQ como sistemas de mensagens, para não precisar instalar
o rabbitMQ manualmente, é possível utilizá-lo em docker (para isso é necessário possuir o Docker instalado).

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Com o comando acima será criado um servidor rabbit básico, porém suficiente para verificar o funcionamento do projeto.

**Rabbit info - subindo em docker local** <br />
**Admin:** http://localhost:15672/ <br />
**host:** localhost <br />
**port:** 15672 <br />
**user:** guest <br />
**password:** guest <br />


Caso queira rodar um cluster rabbit, basta adicionar endereços do cluster ao perfil do projeto.

Para alterar propriedades de configuração do projeto, vá para `cooperative-api/src/main/resources/`:

**Configuração e execução**
- no arquivo [application.properties](src/main/resources/application.properties): a primeira linha aponta
  para o arquivo de configuração a ser utilizado, para rodar localmente na máquina,
  utilize `spring.profiles.active=dev` para utilizar como configuração principal
  da aplicação o arquivo [application-dev.properties](src/main/resources/application-dev.properties)
- altere as configurações conforme necessário para conectar ao banco de dados e sistema e ao RabbitMQ
- depois de devidamente configurado o profile da aplicação, com o comando abaixo é
  executar a aplicação localmente:

```
./gradlew clean build && java -Dspring.profiles.active=dev -jar build/libs/*SNAPSHOT.jar
```
> é importante executar o comando acima dentro da pasta raiz do projeto

Depois de startado projeto estará disponível no endereço: [http://localhost:5000/](http://localhost:5000/)

E a documentação da api poderá ser acessada através do endereço: [http://localhost:5000/swagger-ui.html](http://localhost:5000/swagger-ui.html)

### Docker build

Para gerar build docker do projeto utilize o comando:
``` docker build -t cooperative-api . ```

## Documentação de serviços

A API possui 4 grupos de serviços:
- **Users:** endpoints para criação e manutenção de cadastro de usuários.
  <br />
- **Topics:** endpoints para criação, consulta e manutenção de pautas.
  <br />
- **Sessions:** endpoints para criação, consulta e manutenção de sessões de cotação das paut
  as.
- Por default cada sessão tem duração de 1 minuto, porém

é possível definir um tempo
maior durante a criação da sessão através do atributo `durationMinutes` (tempo em minutos).
- O momento de abertura da sessão para votação pode ser definido através do atributo
  `startTime`, caso não seja definido um tempo para inicio a sessão será automaticamente
  iniciado no momento de abertura
- Após decorrido o tempo definido de duração definido (durationMinutes),
  a sessão será automaticamente encerrada.
- Na v3 da api, quando uma sessão é criada ela é postada na fila de delayed
  do RabbitMQ com um tempo  trinta segundos somado ao tempo restante
  para o fim da sessão. Assim quando a sessão expirar a mensagem é automaticamente movida
  para a fila do RabbitMQ e consumida pela aplicação. Ao consumir a mensagem a aplicação
  Irá verificar se a sessão foi de fato finalizada por limite de tempo.
  **Se a sessão possuir ao menos 3 votos a pauta é fechada automaticamente**.
  <br />
- **Session Votes:** endpoints para registro e consulta de votos
- Na v2 da api, foi implementado o consumo da api `users-api` para validação de
  usuários da api. A api realiza a validação do documento e do cadastro de usuários,
  De acordo com retorno da api o usuário pode não estar apto a votar em uma sessão.

> Os endpoints criados foram pensados de forma a disponibilizar uma interface que seja suficiente
para criação de usuários, pautas, abrir sessões para votação das pautas e
também realizar o registro de votos

## Releases

**cooperative-api v1**

Criação dos serviços da aplicação.

**cooperative-api v3**

Implementação de consumo de api externa para validação de usuários.

**cooperative-api v3 (master)**

Implementação de mensageria com RabbitMQ para processamento de sessões votadas.

## Melhorias Futuras

Melhorias a serem feitas no projeto

**Testes e Qualidade**: <br />
- Aumentar cobertura de testes unitários e de integração
- implementar jacoco e sonar para validação de código e análise de testes,
  buscando no mínimo 80%  cobertura de testes de código.
- Criar testes para os endpoits da aplicação, com objetivo de garantir integridade
  da comunicação com os serviços disponíveis.

**Performance**: <br />
- Realizar testes de carga na aplicação para validar um cenário de alta demanda,
  com o objetivo de verificar consumo de recursos, tratamento de inconsistências
  e performance das operações da aplicação e de banco de dados.

**RabbitMQ**: <br />
- Utilizar um cluster de rabbit para garantir maior disponibilidade
- Analisar possível melhoria no fluxo de processamento das mensagens postadas
- adicionar um controlador para verificar mensagens não publicadas devido a erros de
  comunicação com o servidor rabbit, para que essas mensagens possam ser publicadas
  quando possível.
 

