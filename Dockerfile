FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S eclipse && adduser -S eclipse -G eclipse -u 1001

COPY --from=build /app/target/*.jar app.jar

RUN chown eclipse:eclipse app.jar

USER eclipse

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]