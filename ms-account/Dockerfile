# ---- Stage 1: Pre-cache Dependencies ----
FROM maven:3.9.6-eclipse-temurin-21 AS deps
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

# ---- Stage 2: Build Application ----
FROM maven:3.9.6-eclipse-temurin-21 AS build-prod
WORKDIR /app

COPY --from=deps /root/.m2 /root/.m2
COPY . .

# Un seul package suffit (retrait des stages redondants)
RUN mvn clean package -DskipTests -Pprod

# ---- Stage 3: Production Image ----
FROM eclipse-temurin:21-jre AS prod
WORKDIR /app

# Installation de curl requis par le healthcheck docker-compose
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=build-prod /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
