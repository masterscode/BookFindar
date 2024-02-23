FROM maven:3.8-openjdk-17 AS build

COPY src /app/src

WORKDIR /app

COPY pom.xml /app

RUN mvn clean install


FROM maven:3.8-openjdk-17

WORKDIR /app

COPY --from=build /app/target/FinderBookApp.jar FinderBookApp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","FinderBookApp.jar"]
