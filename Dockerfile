FROM eclipse-temurin:17-alpine

VOLUME /tmp

COPY target/FinderBookApp.jar FinderBookApp.jar

ENTRYPOINT ["java", "-jar", "FinderBookApp.jar"]