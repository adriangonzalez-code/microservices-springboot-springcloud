# Getting Started

## Run Locally

1. Create an account in Docker Hub. https://hub.docker.com/

2. Install DockerDesktop in your local device and log in using your account.

3. Run below commands, those are to create the MySQL DB, before to execute this command, make sure you don't have any MySQL instance running either 3307 and 3308 and 3309 ports
   > docker run -p 3307:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql
   
   > docker run -p 3308:3306 --name cardsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsdb -d mysql
   
   > docker run -p 3309:3306 --name loansdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansdb -d mysql

4. Run the RabbiMQ Image
   > docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3.13-management

5. Run the Redis Image
   > docker run -p 6379:6379 --name eazyredis -d redis

6. Run KeyCloak
   > docker run -p 7080:8080 -d -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.4 start-dev

7. Run Config Server API
8. Run Eureka Server
9. Run Accounts, Cards and Loans Services
10. Run Cloud Gateway Server

**Optional**

9. Go to `https://console.hookdeck.com/` and execute the commands of the step 2
10. If an error message is displayed in the console regarding `401 Unauthorized`, execute the below command
   > hookdeck logout

11. Copy the URL displayed in the console within Webhooks section in Config server GitHub repository


## Setup KeyCloak

Log In:
* user: admin
* password: admin

Create a new client id: eazybank-callcenter-cc
* Client authentication *checked*
* Service accounts roles *checked*

Create roles in Realm Roles left menu:
* ACCOUNTS
* CARDS
* LOANS

Linked the roles to the ClientId from Service Accounts Roles tab

Configure the OAuth2 in your client app


## Run Docker Compose

1. Create the image of each service
    > mvn compile jib:dockerBuild

2. Push each image to Docker Hub
3. Inside the env folder, execute the below command
    > docker compose up -d

4. For stop and destroy the containers
   > docker compose down

## Resources

### Insomnium Requests

> https://github.com/adriangonzalez-code/insomnium-requests

### Properties of Config Server Repository

> https://github.com/adriangonzalez-code/eazyback-config/tree/master
