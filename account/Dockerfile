FROM maven:3.9.6-amazoncorretto-21-debian AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/account.jar account.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "account.jar"]
