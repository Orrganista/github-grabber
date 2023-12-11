FROM openjdk:17-alpine
COPY target/githubgrabber-0.0.1-SNAPSHOT.jar githubgrabber-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "githubgrabber-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev"]