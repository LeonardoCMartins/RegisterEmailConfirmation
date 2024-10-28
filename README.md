# Descrição
O EmailConfirmationService é um projeto desenvolvido em Java com Spring Boot que permite aos usuários se registrarem em um sistema e receberem um email de confirmação para validar seu cadastro. O sistema garante a segurança dos dados do usuário através de criptografia de senhas e gerenciamento de tokens de confirmação.

# Funcionalidades
Registro de usuários com email e senha.
Envio de um email de confirmação após o registro.
Geração de um token de confirmação único.
Validação de usuários por meio de um link de confirmação.

# Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Security
- BCrypt para criptografia de senhas
- JPA/Hibernate para persistência de dados

# Como Executar
Clone o repositório:
```
git clone https://github.com/seu_usuario/email-confirmation-service.git
cd email-confirmation-service
```

Configure seu banco de dados no arquivo application.properties.

Compile o projeto:
```
mvn clean install
```

Execute a aplicação:
```
mvn spring-boot:run
```

# Como Testar
Após a aplicação estar em execução, você pode testar a funcionalidade de registro enviando uma requisição HTTP POST para o endpoint /signup com os dados do usuário no corpo da requisição.
```json
{
	"username": "user",
	"email": "user@gmail.com",
	"password": "123456789"
}
```
Se o registro for bem-sucedido, um email de confirmação será enviado para o endereço especificado.
