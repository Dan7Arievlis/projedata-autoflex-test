# Projedata - Autoflex Teste

Projeto feito como requisito para avaliação de teste prático para vaga de Desenvolvedor(a) Full-Stack na empresa Projedata.
O projeto apresenta implementações de:
* Autenticação de usuário e delegação de autoridades;
* Login e sincronização OAuth2 com Google;
* Persistência de dados usando SQL;
* Auditoria de LGPD para dados sensíveis de usuários;
* CRUD em entidades com associações entre tabelas;
* Interface responsiva para diferentes dispositivos.

## Como usar
O projeto é dividido em 3 branches: main (atual), [docker](https://github.com/Dan7Arievlis/projedata-autoflex-test/tree/docker) (recomendada) e [docker-with-java](https://github.com/Dan7Arievlis/projedata-autoflex-test/tree/docker-with-java). A diferença entre as últimas 2 é que a docker-with-java instala uma versão de Java 21 dentro do container docker para a execução da aplicação. É mais pesada, mas é totalmente contida em si mesma, evitando conflitos de versões.

Mais detalhes de como executar as outras branches podem sr encontrados em suas respectivas páginas.

Essa branch (main), apresenta o códico sem imagem, desta forma, as variáveis de ambiente da aplicação devem ser inseridas manualmente, sem um arquivo ``.env`` associado. Essas variáveis de ambiente são descritas em ``.env.example`` que deverão ser configuradas antes da execução da aplicação em sua JVM.

### Gerar as chaves JWT
   Para isso, é necessário navegar para a root do projeto java do backend, onde haverá um arquivo main java [KeyGenerator.java](https://github.com/Dan7Arievlis/projedata-autoflex-test/blob/main/backend/src/main/java/io/github/dan7arievlis/autoflextest/KeyGenerator.java) que tem o papel de gerar chaves JWT pública e privada, que deverão ser usadas nas variáveis de ambiente.

### Criar banco de dados via docker
Nessa branch, o banco de dados não vem populado automaticamente. Ele deve ser criado e configurado previamente via docker - ou em um banco de dados em sua máquina, tendo em vista que as referências ao banco nas variáveis de ambiente devem ser alteradas. Se for preferível o banco configurado por docker, a variável dentro do ``.env`` deverá funcionar perfeitamente. O comando abaixo deve ser executado para criar a imagem do banco desejado:

```
docker run --name autoflex-test -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=autoflex-test -d --network autoflex-test-network postgres:16.3
```

* Opcionalmente, um gerenciador de banco pgAdmin pode ser conectado na mesma rede com o código:

```
docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -d --network autoflex-test-network dpage/pgadmin4:8.9
```

### Opcional: Autenticação com Google

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
* Salve o progresso e terá acesso ao *ID do Cliente* e às *Chaves secretas do cliente*, copie cada um dos códigos e use-os nas variáveis de ambiente de sua JVM.

URLs:

Frontend: http://localhost:4200

API: http://localhost:8080

pgAdmin: http://localhost:15432

Após popular as variáveis de ambiente de sua JVM e criar o banco de dados via docker, o programa está pronto para ser executado. Inicie o banco de dados, execute o spring boot e, em seguida, inicie o serviço frontend.
