FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build
RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Run
CMD ["java", "-jar", "app.jar"]