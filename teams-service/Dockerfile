# ====== build stage ======
FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :app-service:bootJar --no-daemon

# ====== run stage ======
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/applications/app-service/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]