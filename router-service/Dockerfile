FROM openjdk:8-jdk-alpine
LABEL maintainer="arthurfritzz@gmail.com"
VOLUME /tmp

EXPOSE 9999
ARG JAR_FILE
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/application.jar"]