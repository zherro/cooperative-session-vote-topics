FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1

MAINTAINER zherro
LABEL source="https://github.com/zherro/cooperative-session-vote-topics" \
      author="Cleiton Oliveira"

COPY ./build/libs/*SNAPSHOT.jar  users-api.jar

EXPOSE 5002

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "users-api.jar"]



