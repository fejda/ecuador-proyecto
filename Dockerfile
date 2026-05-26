# Usa una imagen base con JDK 21 y Gradle preinstalado
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copiar archivos de configuración
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN chmod +x gradlew

# 🆕 FORZAR DESCARGA DEL DRIVER POSTGRESQL
RUN ./gradlew dependencies --no-daemon || true
RUN ./gradlew build -x test --no-daemon || true

# Copiar código fuente
COPY src ./src

# 🆕 FORZAR INCLUSIÓN DEL DRIVER EN EL BOOTJAR
RUN ./gradlew bootJar -x test --no-daemon

# Verificar que el driver está en el JAR
RUN jar tf build/libs/*.jar | grep -i postgresql

# Etapa final
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear directorio para datos
RUN mkdir -p /app/data

# Copiar el JAR
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]