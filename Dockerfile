# Common Spring base image: pre-caches shared Maven dependencies for faster microservice builds.
FROM maven:3.9.12-eclipse-temurin-21

WORKDIR /workspace

# Only the pom is needed to resolve dependencies (no source code).
COPY pom.xml .

# Download dependencies and plugins into /root/.m2 (reused by child builds).
RUN mvn -B dependency:go-offline dependency:resolve-plugins
