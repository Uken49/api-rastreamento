FROM openjdk:21 AS build

WORKDIR /app

COPY target/api-rastreamento-1.0.0.jar api-rastreamento.jar

ENTRYPOINT ["java", "-jar", "-DSPRING_APP_ENVIRONMENT=${SPRING_APP_ENVIRONMENT}", "/app/api-rastreamento.jar"]

EXPOSE 8000