"# projedata-autoflex-test" 

1. Copiar env:

cp .env.example .env


2. Build do backend:

cd backend && ./mvnw clean package -DskipTests && cd ..


3. Subir tudo:

docker compose up --build

* Resetar o banco

docker compose down -v


URLs:

Front: http://localhost:4200

API: http://localhost:8080

Banco já sobe populado via database/dump.sql

