# Etapa 1: Construcción con Gradle y Java 21
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copiar archivos de configuración
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Dar permisos y descargar dependencias
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Copiar código fuente y compilar
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# Etapa 2: Imagen final con Java 21 JRE
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear directorio para datos de H2
RUN mkdir -p /app/data

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]