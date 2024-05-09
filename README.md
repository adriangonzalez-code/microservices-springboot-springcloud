# Getting Started

## Run Locally

1. Run MySQL, before to execute this command, make sure you don't have any MySQL instance running either 3307 and 3308 and 3309 ports
   > docker run -p 3307:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql
   
   > docker run -p 3308:3306 --name cardsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsdb -d mysql
   
   > docker run -p 3309:3306 --name loansdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansdb -d mysql

2. Run the RabbiMQ Image
   > docker run -it --rm --name rabbitmq -p -d 5672:5672 -p 15672:15672 rabbitmq:3.13-management

3. Run the Redis Image
   > docker run -p 6379:6379 --name eazyredis -d redis

4. Run Config Server API
5. Run Eureka Server
6. Run Accounts, Cards and Loans Services
7. Run Cloud Gateway Server

**Optional**

8. Go to `https://console.hookdeck.com/` and execute the commands of the step 2
9. If an error message is displayed in the console regarding `401 Unauthorized`, execute the below command
   > hookdeck logout

10. Copy the URL displayed in the console within Webhooks section in Config server GitHub repository

## Run Docker Compose

1. Create the image of each service
    > mvn compile jib:dockerBuild
3. Push each image to Docker Hub
4. Inside the env folder, execute the below command
    > docker compose up -d

5. For stop and destroy the containers
   > docker compose down

## Resources

### Insomnium Requests

> https://github.com/adriangonzalez-code/insomnium-requests

### Properties of Config Server Repository

> https://github.com/adriangonzalez-code/eazyback-config/tree/master