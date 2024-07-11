```markdown
# FakeTwitter API

Bem-vindo à API FakeTwitter, uma simulação das funcionalidades básicas da rede social Twitter, desenvolvida em Java com Spring Boot. Esta API permite criar tweets, visualizar um feed de tweets, gerenciar usuários e autenticar-se usando JWT.

## Requisitos

- Java 17
- Spring Boot 3.x
- Maven

## Configuração do Projeto

### Clonando o Repositório

```bash
git clone https://github.com/joaogabriel/faketwitter.git
cd faketwitter
```

### Configuração do Banco de Dados

Configure as propriedades do banco de dados em `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/faketwitter
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Gerando as Chaves JWT

Gere um par de chaves RSA para uso com JWT:

```bash
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in private_key.pem -out public_key.pem
```

Adicione as chaves geradas em `src/main/resources/application.properties`:

```
jwt.public.key=<conteúdo da chave pública>
jwt.private.key=<conteúdo da chave privada>
```

### Compilando e Executando

```bash
./mvnw clean install
./mvnw spring-boot:run
```

## Funcionalidades

### Configurações

- **AdminConfig**: Configuração para criação automática do usuário administrador.

- **SecurityConfig**: Configuração de segurança usando JWT para autenticação e autorização.

### Modelos

- **Role**: Representa os papéis dos usuários no sistema (admin e basic).

- **Tweet**: Representa um tweet na aplicação.

- **User**: Representa um usuário na aplicação.

### Repositórios

- **RoleRepository**: Repositório para operações CRUD em roles.

- **TweetRepository**: Repositório para operações CRUD em tweets.

- **UserRepository**: Repositório para operações CRUD em usuários.

### Serviços

- **LoginService**: Serviço para autenticação e criação de usuários.

- **TwitterService**: Serviço para criação, deleção de tweets e obtenção do feed.

- **UserService**: Serviço para listagem de usuários.

### Controladores

- **TokenController**: Controlador para login e criação de novos usuários.

- **TwitterController**: Controlador para criação e deleção de tweets, e obtenção do feed.

- **UserController**: Controlador para listagem de usuários.

## Endpoints

### Autenticação

- `POST /login`: Autentica um usuário e retorna um token JWT.
- `POST /create`: Cria um novo usuário.

### Tweets

- `POST /tweet`: Cria um novo tweet.
- `DELETE /tweet/{id}`: Deleta um tweet pelo ID.
- `GET /feed`: Obtém o feed de tweets com paginação.

### Usuários

- `GET /users`: Lista todos os usuários (apenas para administradores).

## Contato

Para mais informações, entre em contato com [João Gabriel](mailto:joaogabriel@example.com).
```

Este README fornece uma visão geral da API FakeTwitter, incluindo requisitos, configuração, funcionalidades principais e endpoints disponíveis. Ele está pronto para ser copiado e usado em um repositório GitHub.
