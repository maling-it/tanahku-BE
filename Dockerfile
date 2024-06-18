FROM khipu/openjdk17-alpine:latest AS build

LABEL org.opencontainers.image.authors="Ahmad Zulfadli"

# COPY .env.docker .env

COPY target/tanahku.jar tanahku.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","tanahku.jar"]