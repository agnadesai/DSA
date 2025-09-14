FROM eclipse-temurin:17-jdk-alpine as build
ARG JAR_FILE=target/*.jar
COPY ./vacationservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
