# Usa una imagen base con JDK 17 y Gradle preinstalado 
FROM gradle:7.6-jdk17 AS builder 
 
WORKDIR /app 
 
COPY build.gradle settings.gradle gradlew ./ 
COPY gradle ./gradle 
 
RUN chmod +x gradlew 
RUN ./gradlew dependencies --no-daemon 
 
COPY src ./src 
RUN ./gradlew bootJar --no-daemon -x test 
 
FROM eclipse-temurin:17-jre-alpine 
 
WORKDIR /app 
 
COPY --from=builder /app/build/libs/*.jar app.jar 
 
EXPOSE 8080 
 
CMD ["java", "-jar", "app.jar"] 
