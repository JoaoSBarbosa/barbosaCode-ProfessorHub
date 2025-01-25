# barbosaCode-ProfessorHub

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-blue?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)

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
