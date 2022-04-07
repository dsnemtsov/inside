curl --location --request POST 'http://localhost:8081/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "user",
    "password": "password"
}'