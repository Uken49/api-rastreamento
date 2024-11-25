FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DSPRING_APP_ENVIRONMENT=test -DskipTests

FROM openjdk:21

WORKDIR /app

COPY --from=build /build/target/api-rastreamento-*.jar api-rastreamento.jar

ENV SPRING_APP_ENVIRONMENT=prod
ENV DATASOURCE_POSTGRES_URL=
ENV DATASOURCE_POSTGRES_PASSWORD=

ENTRYPOINT ["java", "-jar", "/app/api-rastreamento.jar"]
CMD [ "-DSPRING_APP_ENVIRONMENT=${SPRING_APP_ENVIRONMENT}", "-DDATASOURCE_POSTGRES_PASSWORD=${DATASOURCE_POSTGRES_PASSWORD}", "-DDATASOURCE_POSTGRES_URL=${DATASOURCE_POSTGRES_URL}" ]

EXPOSE 8000