
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY ./target/*.jar ./app.jar
EXPOSE 8888
CMD ["java", "-jar", "/app/app.jar"]