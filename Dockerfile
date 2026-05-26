# Dockerfile para Spring Boot con Java 21 
FROM gradle:8.5-jdk21 AS builder 
 
WORKDIR /app 
 
COPY build.gradle settings.gradle gradlew ./ 
COPY gradle ./gradle 
 
RUN chmod +x gradlew 
RUN ./gradlew dependencies --no-daemon 
 
COPY src ./src 
RUN ./gradlew bootJar -x test --no-daemon 
 
FROM eclipse-temurin:21-jre-alpine 
 
WORKDIR /app 
 
COPY --from=builder /app/build/libs/*.jar app.jar 
 
EXPOSE 8080 
 
CMD ["java", "-jar", "app.jar"] 
