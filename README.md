# Projedata - Autoflex Teste

1. Copiar env:

```cp .env.example .env```

1.1. Opcional: Autenticação com Google

* Se quiser usar o login com google, acesse o site https://console.cloud.google.com e crie um novo projeto
  
  <img width="829" height="240" alt="image" src="https://github.com/user-attachments/assets/fb4b64f6-8eea-4b00-be1b-591a3e0bdded" />


* No projeto, navegue para APIs e serviços ativos, na aba de *Credenciais*
  
  <img width="486" height="191" alt="image" src="https://github.com/user-attachments/assets/049f0006-bc1b-408f-b18b-30d3f2f5a02d" />


* Lá, crie um novo *ID do cliente OAuth* e configure o cliente
  
  <img width="620" height="348" alt="image" src="https://github.com/user-attachments/assets/dbb05374-ca42-4b08-9358-195aec744462" />
  
  * NOTA: Se solicitarem para criar a tela de permissão, preencha os campos de acordo

* Crie um novo cliente no botão superior e, em tipo de aplicativo, selecione *Aplicativo web*
* No campo *Origens JavaScript autorizadas* adicione esse link:
  ```http://localhost:4200```
* E no campo *URIs de redirecionamento autorizados* adicione:
  ```http://localhost:8080/login/oauth2/code/google```
* Salve o progresso e terá acesso ao *ID do Cliente* e às *Chaves secretas do cliente*, copie cada um dos códigos e cole nos respectivos lugares no arquivo .env


2. Build do backend:

```cd backend && ./mvnw clean package -DskipTests && cd ..```


3. Subir o container docker:

```docker compose up --build```


 Resetar o banco:

```docker compose down -v```


URLs:

Frontend: http://localhost:4200

API: http://localhost:8080

Dump do banco pré-populado: database/dump.sql

