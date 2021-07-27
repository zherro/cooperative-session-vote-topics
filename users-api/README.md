# Users API

Serviço de validação de usuários


## Como executar o projeto ?

**Configuração e execução**

- Nenhuma configuração adicional é necessária

**Com o comando abaixo o projeto pode ser executado sem problemas**
```
./gradlew clean build && java -jar build/libs/*SNAPSHOT.jar
```
> é importante executar o comando acima dentro da pasta raiz do projeto

Depois de startado projeto estará disponível no endereço: [http://localhost:5005/](http://localhost:5000/)


## Build docker

> executar o comando abaixo na pasta raiz do projeto para gerar imagen docker

```
docker build -t cooperative-users-api .
```

## Endpoints
- gerados de documentos: http://localhost:5005/users/generate/{tipo}?qtd={qtd}
    - ``{tipo}``: informar `cpf` ou `cnpj`
    - ``{qtd}``: numérico - informar quantidade de documentos a serem gerados

- gerados de documentos: http://localhost:5005/users/{tipo}/validate?cpf={doc}
- ``{tipo}``: informar `cpf` ou `cnpj`
- ``{doc}``: número do documento para validação
 
