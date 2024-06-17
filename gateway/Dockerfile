FROM maven:3.9.6-amazoncorretto-21-debian AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/gateway.jar gateway.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "gateway.jar"]
