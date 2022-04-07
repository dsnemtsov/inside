curl --location --request POST 'http://localhost:8081/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "user",
    "password": "password"
}'