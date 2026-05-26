# Usa una imagen base con JDK 21 y Gradle preinstalado
FROM gradle:8.5-jdk21 AS builder

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Gradle primero (para cachear dependencias)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Da permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Descarga dependencias (esto se cachea si no cambia build.gradle)
RUN ./gradlew dependencies --no-daemon

# Copia el código fuente
COPY src ./src

# Construye el archivo JAR (omite los tests para que sea más rápido)
RUN ./gradlew bootJar -x test --no-daemon

# ---- Etapa 2: Imagen final más pequeña ----
FROM eclipse-temurin:21-jre-alpine

# Directorio de trabajo
WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

# Crea el directorio para la base de datos H2 (persistente)
RUN mkdir -p /app/data

# Expone el puerto que usa tu app
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]