# Usa una imagen base con JDK 17 y Gradle preinstalado
FROM gradle:7.6-jdk17 AS builder

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Gradle
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Da permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Descarga dependencias
RUN ./gradlew dependencies --no-daemon

# Copia el código fuente
COPY src ./src

# Construye el archivo JAR
RUN ./gradlew bootJar --no-daemon -x test

# ---- Etapa 2: Imagen final ----
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo
WORKDIR /app

# Copia el JAR construido
COPY --from=builder /app/build/libs/*.jar app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]