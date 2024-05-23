# Build
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY src /src
COPY pom.xml .
RUN mvn -f ./pom.xml clean package

# Run
FROM openjdk:17
COPY ./target/MPP-Backend-0.0.1-SNAPSHOT.jar ./MPP-Backend-0.0.1-SNAPSHOT.jar
EXPOSE 8443
USER 10014
ENTRYPOINT ["java","-jar","./MPP-Backend-0.0.1-SNAPSHOT.jar"]