FROM openjdk:17-slim


WORKDIR /app

COPY target/parqueadero-0.0.1-SNAPSHOT.jar /app/parqueadero.jar

EXPOSE 3000

CMD ["java", "-jar", "/app/parqueadero.jar"]