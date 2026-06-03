FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/*.jar app.jar
COPY src/main/webapp /app/webapp
ENTRYPOINT ["java", "-jar", "app.jar"]