# Dockerfile para proyecto Java generico sin Spring Boot 
FROM gradle:7.6-jdk17 AS builder 
 
WORKDIR /app 
 
COPY build.gradle settings.gradle gradlew ./ 
COPY gradle ./gradle 
 
RUN chmod +x gradlew 
RUN ./gradlew build -x test --no-daemon 
 
COPY src ./src 
RUN ./gradlew jar --no-daemon 
 
FROM eclipse-temurin:17-jre-alpine 
 
WORKDIR /app 
 
COPY --from=builder /app/build/libs/*.jar app.jar 
 
EXPOSE 8080 
 
CMD ["java", "-jar", "app.jar"] 
