# Jsonplaceholder demo Apis
The Api Guid [Link](https://jsonplaceholder.typicode.com/)

## Requirements
* jdk 8
* maven
* docker
<p>This project a demo of Jsonplaceholder Implementations in Spring boot and secure by Spring security and JWT</p>
<p>Project DB config is simply just need install docker and run </p>


> docker compose up -d

<p>the docker config and run Postgres and create needed database and user to connect form application</p>

<p>There is several apis in the project the all GET apis don't need any access token but for use POST,PUT,PATCH,DELETE need to access token</p>

<p>For get access token there is login api and user add with InMemory to simply</p>

```bash
curl --location --request POST 'http://localhost:8080/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "admin",
    "password": "admin"
}'
```
This user had ADMIN role

if you want check other role to have no access the apis
there is user with  role USER

```bash
curl --location --request POST 'http://localhost:8080/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user",
    "password": "user"
}'
```
if you give this user's access token the api response 403

There is some UnitTest for test apis to run it and test only run the

> mvn test