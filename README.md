# API REST de Autenticação com JWT - Spring Boot 3 & Spring Security 6

Este projeto é uma API didática desenvolvida para demonstrar a implementação de autenticação e autorização utilizando tokens JWT (JSON Web Token) em uma aplicação Spring Boot 3 com Spring Security 6.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.3**
- **Spring Security 6**
- **JJWT (Java JWT)** 0.12.6
- **Spring Data JPA** & **H2 Database** (em memória)
- **Bean Validation** (Jakarta)
- **Maven**

## 🛠️ Pré-requisitos

- JDK 21 ou superior instalado.
- Maven 3.8+ instalado.

## ⚙️ Configuração

### Chave Secreta do JWT

Por segurança, a chave secreta utilizada para assinar os tokens não deve estar exposta no código-fonte. No arquivo `application.yml`, ela está configurada para ser lida da variável de ambiente `SECURITY_JWT_SECRET_KEY`.

Caso a variável não esteja definida, o sistema utiliza uma chave padrão (apenas para fins de desenvolvimento).

**Como configurar a chave localmente (PowerShell):**
```powershell
$env:SECURITY_JWT_SECRET_KEY = "SuaChaveSecretaMuitoForteEmBase64Aqui"
```

**Como configurar a chave localmente (Linux/macOS):**
```bash
export SECURITY_JWT_SECRET_KEY="SuaChaveSecretaMuitoForteEmBase64Aqui"
```

> **Nota:** A chave deve possuir pelo menos 256 bits (32 caracteres). Se for utilizar Base64, garanta que o resultado decodificado tenha 32 bytes. No exemplo padrão do `application.yml`, utilizamos uma string simples de 32+ caracteres.

## 👥 Usuários para Teste

A aplicação cria automaticamente dois usuários ao iniciar:

1.  **Usuário comum:**
    - **Username:** `user`
    - **Senha:** `user123`
    - **Role:** `USER`
2.  **Administrador:**
    - **Username:** `admin`
    - **Senha:** `admin123`
    - **Role:** `ADMIN`

## 📡 Endpoints

| Método | Endpoint  | Acesso           | Descrição                                      |
| :----- | :-------- | :--------------- | :--------------------------------------------- |
| `POST` | `/login`  | Público          | Autentica e retorna o JWT                      |
| `GET`  | `/perfil` | Autenticado      | Retorna os dados do perfil logado              |
| `GET`  | `/admin`  | Apenas **ADMIN** | Endpoint protegido para administradores        |

## 🧪 Demonstração dos Cenários (via cURL)

### 1. Login com USER
```bash
curl -X POST http://localhost:8080/login \
     -H "Content-Type: application/json" \
     -d '{"username": "user", "password": "user123"}'
```
*Resultado: 200 OK com o Token.*

### 2. USER acessando /perfil
```bash
curl -X GET http://localhost:8080/perfil \
     -H "Authorization: Bearer <COLE_O_TOKEN_AQUI>"
```
*Resultado: 200 OK.*

### 3. USER acessando /admin
```bash
curl -X GET http://localhost:8080/admin \
     -H "Authorization: Bearer <COLE_O_TOKEN_AQUI>"
```
*Resultado: 403 Forbidden.*

### 4. Login com ADMIN
```bash
curl -X POST http://localhost:8080/login \
     -H "Content-Type: application/json" \
     -d '{"username": "admin", "password": "admin123"}'
```
*Resultado: 200 OK com o Token.*

### 5. ADMIN acessando /admin
```bash
curl -X GET http://localhost:8080/admin \
     -H "Authorization: Bearer <COLE_O_TOKEN_ADMIN_AQUI>"
```
*Resultado: 200 OK.*

### 6. Acesso sem Token
```bash
curl -X GET http://localhost:8080/perfil
```
*Resultado: 401 Unauthorized.*

## 📖 Explicação dos Códigos HTTP

- **200 OK:** Requisição processada com sucesso.
- **400 Bad Request:** Dados de entrada inválidos (ex: falta de usuário ou senha).
- **401 Unauthorized:** Token ausente, inválido ou expirado. Indica que a identidade não pôde ser verificada.
- **403 Forbidden:** O usuário está autenticado, mas não possui a Role necessária (`ADMIN`) para acessar o recurso.

## 🧪 Como executar os testes
```bash
mvn test
```

## 🏗️ Estrutura do Projeto
- `config/`: Configurações gerais e inicialização de dados.
- `controller/`: Camada de exposição da API.
- `dto/`: Objetos de transferência de dados (Request/Response).
- `exception/`: Tratamento global de erros.
- `model/`: Entidades de domínio.
- `repository/`: Interface de acesso ao banco de dados (H2).
- `security/`: Toda a lógica de JWT e segurança.
- `service/`: Regras de negócio e autenticação.
