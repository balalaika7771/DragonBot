FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/your-application.jar /app/your-application.jar

EXPOSE 8080

CMD ["java", "-jar", "your-application.jar"]
