# backend
## initialized the application 
cp Sampe.env .env

docker compose build

docker compose up -d
## ran the application

for run the application : 

for create account : 
curl -X POST http://localhost/auth/register  -H "Content-Type: application/json"   -d '{
    "email": "exemple@email.com",
    "password": "votre_mot_de_passe",
    "firstname": "Jean",
    "lastname": "Dupont",
    "address": "123 rue Principale",
    "zipcode": "1000",
    "locate": "Lausanne",
    "phoneNumber": "+41210000000"
  }'

authentification : 
http://localhost/auth/login  