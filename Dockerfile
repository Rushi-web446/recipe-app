# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Production Stage
FROM openjdk:17-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/recipeApp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
