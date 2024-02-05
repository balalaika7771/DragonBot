FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/DragCaveBot-0.0.1-SNAPSHOT.jar /app/DragCaveBot.jar

EXPOSE 8080

CMD ["java", "-jar", "DragCaveBot.jar"]
