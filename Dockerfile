# 1) Build stage: compiles and packages the shaded jar
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests clean package
# DEBUG: list target output
RUN ls -l /src/target

# 2) Run stage: slim JRE image
# runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/target/seMethods-0.1.0.2.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
