# Use a lightweight OpenJDK image
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built jar from the build context
COPY target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
