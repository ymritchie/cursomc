FROM openjdk:8-jdk-alpine
LABEL maintainer "Yanisley Mora Ritchie <ymritchie@gmail.com>"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080