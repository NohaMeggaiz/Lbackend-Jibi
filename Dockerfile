FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-ea-28-jdk-slim
COPY --from=build /target/Backend2-0.0.1-SNAPSHOT.jar Backend2.jar

EXPOSE 8090
ENTRYPOINT ["java","-jar","Backend2.jar"]