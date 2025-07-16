
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY ./target/*.jar ./app.jar
EXPOSE 8989
CMD ["java", "-jar", "/app/app.jar"]