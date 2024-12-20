FROM openjdk:17-jdk-slim

LABEL authors="mrxat"
WORKDIR /app
COPY build/libs/crud-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
