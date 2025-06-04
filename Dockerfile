# Stage 1: Build the Java application
# Uses a Maven image with OpenJDK 17, matching your project's Java version.
# Changed FROM tag to a commonly available one: maven:3-jdk-17
FROM maven:3-jdk-17 AS build

# Set the working directory inside the build container.
WORKDIR /app

# Copy the entire project (including pom.xml and source code) into the container.
COPY . .

# Run the Maven build command.
# 'clean' removes any previous build artifacts.
# 'install' compiles the code, runs tests (skipped with -DskipTests), and packages the application into a JAR file.
# -DskipTests is used to speed up the build process by skipping unit tests, which are usually run as part of CI/CD.
RUN mvn clean install -DskipTests

# Stage 2: Create the final production-ready image
# Uses a lightweight OpenJDK 17 image for the final application runtime.
FROM openjdk:17-slim

# Set the working directory inside the final application container.
WORKDIR /app

# Copy the generated JAR file from the 'build' stage to the current stage.
# The JAR file name 'recipeApp-0.0.1-SNAPSHOT.jar' is taken directly from your pom.xml's artifactId and version.
# It's renamed to 'app.jar' for simplicity within the container.
COPY --from=build /app/target/recipeApp-0.0.1-SNAPSHOT.jar app.jar

# Define the command that will be executed when the Docker container starts.
# This runs your Spring Boot application using the generated JAR file.
ENTRYPOINT ["java","-jar","app.jar"]
