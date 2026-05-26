# ── Etapa 1: Build con Gradle y Java 21 ──────────────────────────────
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# ── Etapa 2: Imagen final ligera con JRE 21 ───────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
