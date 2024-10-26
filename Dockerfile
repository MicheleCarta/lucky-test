FROM openjdk:17-jdk-buster as artifact
WORKDIR app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/user-service-latest.jar .

ARG VERSION=latest
ENV APPLICATION_VERSION=$VERSION

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "user-service-latest.jar"]
