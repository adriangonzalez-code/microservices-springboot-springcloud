# Getting Started

## Run Locally

1. Run the RabbiMQ Image

    > docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

2. Run Config Server API
3. Run Accounts, Cards and Loans Services
4. Go to `https://console.hookdeck.com/` and execute the commands of the step 2
5. If an error message is displayed in the console regarding `401 Unauthorized`, execute the below command
   > hookdeck logout
6. Copy the URL displayed in the console within Webhooks section in Config server GitHub repository

## Run Docker Compose

1. Create the image of each service
    > mvn compile jib:dockerBuild
3. Push each image to Docker Hub
4. Inside the env folder, execute the below command
    > docker compose up -d