# barbosaCode-ProfessorHub

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=spring)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Enabled-brightgreen?logo=springsecurity)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Enabled-blue?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Flyway](https://img.shields.io/badge/Flyway-Enabled-orange?logo=flyway)
![Lombok](https://img.shields.io/badge/Lombok-Enabled-brightgreen?logo=lombok)
![JUnit](https://img.shields.io/badge/JUnit-Tested-red?logo=junit5)
![Último Commit](https://img.shields.io/github/last-commit/JoaoSBarbosa/barbosaCode-ProfessorHub?logo=github)


**barbosaCode-ProfessorHub** é um sistema desenvolvido em **Java** e **Spring Boot** para gerenciamento de aulas particulares entre professores e alunos. A plataforma permite o cadastro, autenticação, e gestão de informações tanto de professores quanto de alunos, facilitando a busca, cadastro de aulas, e a comunicação entre ambos.

## Funcionalidades

### Requisitos Funcionais

- **Cadastro de professor**: Permite que professores se cadastrem, informando nome, email, idade, descrição, valor da hora/aula e senha.
- **Login**: Professores podem se autenticar usando email e senha, com a possibilidade de realizar logout.
- **Atualização de dados pessoais**: Professores podem atualizar suas informações pessoais, como nome, email, idade, descrição, valor da hora/aula, senha e foto de perfil.
- **Listagem de alunos**: Professores podem visualizar todas as solicitações de aulas particulares que receberam.
- **Exclusão de professor**: Professores podem excluir sua conta da plataforma.
- **Busca de professores**: Alunos podem buscar professores pelo nome.
- **Detalhes do professor**: Alunos podem visualizar os detalhes de um professor específico.
- **Cadastro de aula**: Alunos podem cadastrar aulas particulares, informando nome, email, data e horário.

### Requisitos Não Funcionais

- **Segurança**: Senhas dos usuários são criptografadas para garantir maior segurança da aplicação e proteção dos dados pessoais.
- **Ética**: Nenhum dado confidencial de outro usuário será exposto.
- **Confiabilidade**: O sistema deve estar disponível por no mínimo 95% do tempo.

## Documentação de Análise

- [Requisitos Funcionais](analysis/functional-requirements.md)
- [Requisitos Não Funcionais](analysis/non-functional-requirements.md)
- [Diagrama de Classe](analysis/use-cases.md)

### Rotas

| Rota                                 | Método | Descrição                                         | Requer Autenticação   |
|--------------------------------------|--------|---------------------------------------------------|-----------------------|
| `/api/teachers`                      | GET    | Lista os professores                              | Não                   |
| `/api/teachers/{teacher_id}`         | GET    | Detalhes do professor                             | Não                   |
| `/api/students/{teacher_id}` | POST   | Cadastra uma solicitação de aula                  | Não                   |
| `/api/teachers`                      | POST   | Cadastra um professor                             | Não                   |
| `/api/teachers`                      | PUT    | Atualiza os dados do professor logado             | Sim                   |
| `/api/teachers/photo`                | POST   | Atualiza a foto de um professor                   | Sim                   |
| `/api/teachers/students`             | GET    | Lista as solicitações de aula do professor logado | Sim                   |
| `/api/teachers`                      | DELETE | Exclui o professor logado                         | Sim                   |
| `/api/auth/login`                    | POST   | Faz login                                         | Não                   |
| `/api/auth/refresh`                  | POST   | Atualiza o token de acesso                        | Não                   |
| `/api/auth/logout`                   | POST   | Faz logout                                        | Sim                   |
 | `/api/teachers/description/`         | GET   | Lista os professores por descrição

## Exemplos de Requisição e Retorno

### Cadastro de Professor

**Requisição**:
```http
POST /api/teachers
Content-Type: application/json
```
**Body**:
```json
{
  "name": "João da Silva",
  "email": "joao.silva@email.com",
  "age": 35,
  "description": "Professor de Matemática",
  "hourly_rate": 50.0,
  "password": "senha123"
}
```

**Resposta (201 Created)**:
```json
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao.silva@email.com",
  "age": 35,
  "description": "Professor de Matemática",
  "hourly_rate": 50.0
}
```

---

### Login de Professor

**Requisição**:
```http
POST /api/auth/login
Content-Type: application/json
```
**Body**:
```json
{
  "email": "joao.silva@email.com",
  "password": "senha123"
}
```

**Resposta (200 OK)**:
```json
{
  "access_token": "eyJhbGciOiJIUzUxMiIsIn...",
  "refresh_token": "dGhpcyBpcyBhIHRlc3Qgc3...",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

---

### Listagem de Professores

**Requisição**:
```http
GET /api/teachers
```

**Resposta (200 OK)**:
```json
[
  {
    "id": 1,
    "name": "João da Silva",
    "description": "Professor de Matemática",
    "hourly_rate": 50.0
  },
  {
    "id": 2,
    "name": "Maria Oliveira",
    "description": "Professora de Física",
    "hourly_rate": 60.0
  }
]
```

---

### Atualização de Dados do Professor

**Requisição**:
```http
PUT /api/teachers
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Body**:
```json
{
  "name": "João da Silva Atualizado",
  "email": "joao.silva.atualizado@email.com",
  "age": 36,
  "description": "Professor de Matemática Avançada",
  "hourly_rate": 55.0
}
```

**Resposta (200 OK)**:
```json
{
  "id": 1,
  "name": "João da Silva Atualizado",
  "email": "joao.silva.atualizado@email.com",
  "age": 36,
  "description": "Professor de Matemática Avançada",
  "hourly_rate": 55.0
}
```

---

## Tecnologias

Este projeto utiliza as seguintes tecnologias:

- **Java 17**
- **Spring Boot 3.0**
- **Spring Security** para autenticação e controle de acesso
- **Spring Data JPA** para interação com o banco de dados
- **MySQL** como banco de dados
- **Flyway** para versionamento de banco de dados
- **Lombok** para redução de código boilerplate
- **JUnit** para testes automatizados

## Instalação

Para rodar o projeto localmente, siga os passos abaixo:

1. Clone o repositório:
   ```bash
   git clone https://github.com/usuario/barbosaCode-ProfessorHub.git
   cd barbosaCode-ProfessorHub
   ```
2. Configure o banco de dados MySQL.
3. Execute o projeto com:
   ```bash
   ./mvnw spring-boot:run
   ```