# Build stage
FROM maven:3.9.7-amazoncorretto-21 AS build
WORKDIR /app

# Copy Maven settings
COPY src/main/resources/settings.xml /root/.m2/settings.xml

# Copy only pom.xml first and install dependencies with retry mechanism
COPY pom.xml .
RUN mvn dependency:resolve \
    dependency:resolve-plugins \
    --fail-never \
    -B \
    --no-transfer-progress

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests --no-transfer-progress

# Run stage
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]