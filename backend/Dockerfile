FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ecommerce ./ecommerce
WORKDIR /app/ecommerce
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/ecommerce/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]