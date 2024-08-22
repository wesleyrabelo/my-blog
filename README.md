# Rest API Blog
![Java's logo](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring's logo](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Postgres's logo](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT's log](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)

Esse é um projeto que criei em meu tempo livre para exercitar e compreender JAVA, Spring Book Framework, Rest, Mockito, JUnit e
outras ferramentas mais a fundo. Ainda está meio cru, há outras funcionalidades que irei adicionar com o tempo.

### Descrição :writing_hand:

O projeto possui, basicamente, duas entidades: **ApiUsers** e **ApiPosts**. Ambas entidades possuem seus respectivos endpoints
CRUD, a entidade ApiUser também possui um endpoint único para logar na aplicação, onde é gerado um JWT que será utilizado
para fazer a validação do usuário nos outros pontos de acesso da API.

### Técnologias utilizadas :man_technologist:
Foram utilizadas as seguintes técnologias do Spring Boot Framework :leaves:
- **Spring Boot Starter Web**: Para criação da aplicação Web
- **Spring Boot Starter Data Jpa**: Para integração com o banco de dados
- **Spring Boot Starter Security**: Para a validação do JWT

Além do ambiente Spring, também foram utilizadas as técnologias abaixo :point_down:
- **Auth0 JWT**: Criação e gerenciamento do JWT
- **Hibernate validator**: Adicionar constraints na criação dos objetos POJO
- **Postgresql**: Gerenciador do banco de dados da aplicação
- **H2**: Banco de dados embarcado para facilitar e agilizar os testes unitários dos JpaRepositories
- **JUnit5**: Para os testes unitários
- **Mockito**: Utilizado com conjunto com o JUnit5 para os testes unitários

### Alguns dos desafios enfrentados

Meu maior desafio foi e está sendo a criação dos testes com Mockito, foi meu primeiro contato com o framework e encontro algumas
dificuldades de como utilizá-lo corretamente.

### Listagem dos endpoints :compass:
#### ApiUsers
- **Post**
  - Criar: http://localhost:8080/user
  - Logar: http://localhost:8080/user/login
- **Get**
  - Listar todos: http://localhost:8080/user
  - Localizar pelo id: http://localhost:8080/user/{id}
- **Put**
  - Atualizar pelo id: http://localhost:8080/user/{id}
- **Delete**
  - Deletar pelo id: http://localhost:8080/user/{id}
#### ApiPosts
- **Post**
  - Criar: http://localhost:8080/post
- **Get**
  - Listar todos: http://localhost:8080/post
  - Localizar pelo id: http://localhost:8080/post/{id}
  - Listar todos do usuário autenticado: http://localhost:8080/post/user
- **Put**
  - Atualizar pelo id: http://localhost:8080/post/{id}
- **Delete**
  - Deletar pelo id: http://localhost:8080/post/{id}
