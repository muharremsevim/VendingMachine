FROM openjdk:21-jdk-slim
LABEL authors="sevim"

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/vending-machine-api.jar"]