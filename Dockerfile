# Usa una imagen base con JDK 17 y Gradle preinstalado
FROM gradle:7.6-jdk17 AS build

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
RUN ./gradlew bootJar --no-daemon -x test

# ---- Etapa 2: Imagen final más pequeña ----
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo
WORKDIR /app

# Copia el JAR construido desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto que usa tu app (Spring Boot por defecto es 8080)
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]