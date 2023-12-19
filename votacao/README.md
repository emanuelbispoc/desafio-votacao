# Desafio votação
Nesse repositório se encontra a minha implementação do desafio de votação, onde o objetivo do tema é desenvolver uma 
aplicação que registre as decisões tomadas em assembleias, por votação.

## Tecnologias e ferramentas utilizadas

- **Java 17**;
- **Docker**;
- **Spring Boot (3.1.3)**;
- **Spring Cloud OpenFeign (2022.0.4)**;
- **Spring Boot Validation**;
- **Spring Boot Data JPA**;
- **Mapstruct (1.5.5.Final)**;
- **Flyway**:
- **PostgreSQL**;
- **Lombok**;
- **RestAssured**;
- **WireMock**;

## Pré-requisitos

- **Java 17 ou superior**
- **PostgreSQL (Opcional)** 
- **Docker**

## Instalando o projeto

### Clonando o repositório

Para clonar o repositório basta executar em seu terminal o comando

```
git clone https://github.com/emanuelbispoc/desafio-votacao.git
```

### Configurando variáveis
Quando você for executar a aplicação com o `docker-compose` as variáveis serão configuradas automáticamente, mesmo assim 
se preferir utilizar eventual postgreSQL que esteja instalado em sua maquina, você precisará configurar as seguintes variáveis de ambiente:

```
DB_URL=url_de_sua_conexao_local
DB_USER=seu_usuário_local
DB_PASSWORD=sua_senha_local
```

### Executando a aplicação

Primeiramente precisamos leventar nossos contâiners. Execute o seguinte comando na raiz do projeto:

```shell
docker-compose up -d
```

Após os contâiners estarem de pé podemos executar o projeto:

```shell
./gradlew bootRun
```

## Como usar

#### Cadastrando um associado no sistema

````http
POST /v1/associados
````

| Parâmetro | Tipo     | Descrição                                                |
|:----------| :------- |:---------------------------------------------------------|
| `nome`    | `string` | Campo obrigatório e deve ser informado no RequestBody    |
| `cpf`     | `string` | Deve conter 11 caracteres e ser informado no RequestBody |


O CPF sempre será validado na solicitação de cadastro do associado.

#### Registrando voto em uma sessão

````http
POST /v1/{id}/registra-voto
````

| Parâmetro     | Tipo     | Descrição                                                                           |
|:--------------| :------- |:------------------------------------------------------------------------------------|
| `id`          | `integer` | O ID da sessão em andamento deve ser passado por PathParam                          |
| `associadoCpf`| `string` | O CPF do associado deve estar previamente cadastrado e ser informado no RequestBody |
| `voto`        | `string` | Dever ser o valor entre SIM e NÃO no RequestBody                                    |

Para cada solicitação de registro do voto, é validado a situação do CPF do associado para poder prosseguir e ser atendido.
Para garantir que os votos não sejam duplicados foi utilizado chave primaria composta na entidade do voto da sessão:

````java
public class VotoSessao {
    @EmbeddedId
    private VotoPK id;

    @Enumerated(EnumType.STRING)
    private Voto decisao;

    public VotoSessao(Voto decisao, Associado associado) {
        id = new VotoPK();
        id.setAssociado(associado);
        this.decisao = decisao;
    }

    public void setSessao(Sessao sessao) {
        id.setSessao(sessao);
    }
}
````

`VotoPK` é a entidade que carrega as entidades donas do id's que compõem a chave primaria 

````java
@Embeddable
public class VotoPK {
    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "associado_id")
    private Associado associado;
}
````

