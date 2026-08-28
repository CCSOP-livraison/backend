# backend
## initialized the application 
cp Sampe.env .env

docker compose build

docker compose up -d
## ran the application

for run the application : 

for test simple authentification : 

curl -d '{"email":"user@domain.com","password":"password123"}' -H "Content-Type: application/json"  -X POST  http://localhost/auth/login  