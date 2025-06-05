# Stage 1: Build the Java application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Create the final production-ready image
FROM openjdk:17-slim
WORKDIR /app
EXPOSE 8080 # This line should be just "EXPOSE 8080"
COPY --from=build /app/target/recipeApp-0.0.1-SNAPSHOT.jar app.jar

# REVERT THIS LINE: No --server.port=$PORT here
ENTRYPOINT ["java","-jar","app.jar"]