# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Information around who maintains the image
MAINTAINER driagon.com

# Add the application's jar to the image
COPY target/accounts-service-0.0.1-SNAPSHOT.jar accounts-service-0.0.1-SNAPSHOT.jar

# Execute the application
ENTRYPOINT ["java", "-jar", "accounts-service-0.0.1-SNAPSHOT.jar"]