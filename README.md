# backend
## initialized the application 
cp Sampe.env .env
docker compose build
docker compose up -d
## ran the application

for run the application : 
mvn spring-boot:run

for test simple authentification : 
curl -u admin:secret123  http://localhost:8080/api/login  