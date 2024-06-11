# Build stage
FROM maven:3.8.4-openjdk-17 AS builder

# Define the working directory in the container
WORKDIR /app

# Copy the project files to the container
COPY . /app/

# Optional: Install additional dependencies if required
# RUN apt-get update && apt-get install -y some-package

# Run Maven to clean and package the project
RUN mvn clean package

# Runtime stage
FROM openjdk:17-alpine

# Define the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/target/*.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
