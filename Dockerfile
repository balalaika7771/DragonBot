FROM adoptopenjdk:21-jre-hotspot

WORKDIR /app

COPY target/DragCaveBot-0.0.1-SNAPSHOT.jar /app/DragCaveBot.jar

CMD ["java", "-jar", "DragCaveBot.jar"]
