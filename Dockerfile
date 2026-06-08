# ─────────────────────────────────────────────────────────────
# Eclipse Protocol — Dockerfile
# Build  : Maven 3.9 + Java 17
# Banco  : Oracle (oracle.fiap.com.br:1521:ORCL)
# Usuário: eclipse (sem root)
# WORKDIR: /app (definido explicitamente)
# EXPOSE : 8080
# ENV    : variável de ambiente definida
# ─────────────────────────────────────────────────────────────

FROM maven:3.9-eclipse-temurin-17-alpine

LABEL maintainer="equipe-eclipse@fiap.com.br"
LABEL description="Eclipse Protocol - IoT Agronegócio - FIAP Global Solution 2026"
LABEL version="1.0.0"

# Usuário sem privilégios
RUN addgroup -S eclipse && adduser -S eclipse -G eclipse -u 1001

# Diretório de trabalho
WORKDIR /app

# Variável de ambiente
ENV APP_NAME="eclipse-protocol" \
    APP_ENV="prod" \
    APP_PORT=8080

# Cache de dependências Maven
COPY pom.xml ./
RUN mvn dependency:go-offline -q

# Código-fonte e build
COPY src ./src
RUN mvn package -DskipTests -q

RUN chown -R eclipse:eclipse /app

# Copia JAR para local limpo
RUN cp target/*.jar app.jar && chown eclipse:eclipse app.jar

# Usuário sem privilégios
USER eclipse

# Porta exposta
EXPOSE 8080

# Inicia com perfil prod e banco Oracle
ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-jar", "app.jar"]