# Сборка
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle
COPY ./src ./src
RUN ./gradlew bootJar --no-daemon

# Создаём второй минимальный образ с jar-файлом
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Запуск
ENTRYPOINT ["java", "-jar", "app.jar"]

