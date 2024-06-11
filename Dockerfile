# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/Backend2-0.0.1-SNAPSHOT.jar Backend2.jar

EXPOSE 8090
ENTRYPOINT ["java","-jar","Backend2.jar"]
