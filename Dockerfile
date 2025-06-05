FROM openjdk:17-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/recipeApp-0.0.1-SNAPSHOT.jar app.jar