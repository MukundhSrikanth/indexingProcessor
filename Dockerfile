FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/indexing-processor-all.jar app.jar

VOLUME /input

ENTRYPOINT ["java", "-jar", "app.jar"]
