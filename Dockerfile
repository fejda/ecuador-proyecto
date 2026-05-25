# Dockerfile para proyecto Java 
FROM eclipse-temurin:17-jdk-alpine 
 
WORKDIR /app 
 
COPY . . 
 
EXPOSE 8080 
 
CMD ["sh", "-c", "echo '=== PROYECTO ECUADOR ===' && ls -la"] 
