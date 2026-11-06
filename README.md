# Biblioteca CloudTech Solutions

Aplicação web em Java Spring Boot para o gerenciamento dos livros do acervo da Biblioteca CloudTech Solutions. Permite consultar os livros, cadastrar novos livros e alterar as informações dos livros já existentes.

Trabalho da disciplina Desenvolvimento Backend (UNISENAI), Aula 11, Exercício 2.

Os dados ficam em memória, ou seja, são perdidos quando a aplicação é encerrada.

## Tecnologias

- Java 21
- Spring Boot 4.1.2 (Spring Web e Validation)
- Maven (via Maven Wrapper `mvnw`)
- GitHub Actions

## Endpoints

Todas as respostas são em JSON.

| Método | Endpoint               | Descrição                                   |
|--------|------------------------|---------------------------------------------|
| GET    | `/`                    | Mensagem de boas-vindas                     |
| GET    | `/livros`              | Lista os livros cadastrados                 |
| POST   | `/livros/cadastrar`    | Cadastra um novo livro                      |
| PUT    | `/livros/alterar/{id}` | Altera as informações de um livro existente |
| GET    | `/livros/{id}`         | Consulta um livro pelo ID                   |
| GET    | `/documentacao`        | Lista os endpoints da aplicação             |

Campos do livro: `id`, `titulo`, `autor`, `editora`, `ano`, `categoria` e `status` (`Disponível` ou `Emprestado`). O `id` é gerado automaticamente no cadastro.

Exemplo de corpo para `POST /livros/cadastrar` e `PUT /livros/alterar/{id}`:

```json
{
  "titulo": "Banco de Dados para Iniciantes",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2023,
  "categoria": "Informática",
  "status": "Disponível"
}
```

Respostas:

- `POST /livros/cadastrar` retorna `201` com o livro cadastrado.
- `PUT /livros/alterar/{id}` retorna `200` com o livro alterado.
- `GET /livros/{id}` e `PUT /livros/alterar/{id}` retornam `404` se o livro não existir.
- `POST` e `PUT` retornam `400` se algum campo estiver vazio ou se o status for inválido.

## Como executar

Pré-requisito: Java 21 instalado.

```bash
./mvnw spring-boot:run
```

No Windows (cmd ou PowerShell):

```bash
mvnw.cmd spring-boot:run
```

A aplicação fica disponível em `http://localhost:8080`.

## Como testar

### Testes automatizados

Os testes ficam na pasta `tests/` e cobrem cada endpoint. Para executar:

```bash
./mvnw verify
```

O mesmo comando é executado pelo pipeline do GitHub Actions (`.github/workflows/pipeline.yml`) a cada push e pull request na branch `main`.

### Testes manuais (Thunder Client)

Com a aplicação em execução, faça as requisições abaixo no Thunder Client:

1. `GET http://localhost:8080/` - mensagem de boas-vindas.
2. `POST http://localhost:8080/livros/cadastrar` com o JSON de exemplo no corpo - retorna o livro com `id`.
3. `GET http://localhost:8080/livros` - lista com o livro cadastrado.
4. `GET http://localhost:8080/livros/1` - livro de ID 1.
5. `PUT http://localhost:8080/livros/alterar/1` com o JSON alterado no corpo - retorna o livro atualizado.
6. `GET http://localhost:8080/documentacao` - lista de endpoints.

## Estrutura do repositório

```
biblioteca-cloudtech/
├── src/
├── tests/
├── README.md
├── .gitignore
├── pom.xml
└── .github/
    └── workflows/
        └── pipeline.yml
```
