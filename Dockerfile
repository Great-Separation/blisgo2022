FROM adoptopenjdk:17-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM adoptopenjdk:17-jdk-hotspot
COPY --from=builder build/libs/*.jar app.jar

ARG ENVIRYNMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRYNMENT}

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]