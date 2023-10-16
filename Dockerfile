FROM gradle:8.3.0-jdk17 as builder
WORKDIR /app
# Копируем файлы с исходным кодом в контейнер
COPY ./ ./
# Выполняем сборку приложения
RUN gradle clean build

# Создаем отдельный образ для запуска приложения
FROM openjdk:17-slim
WORKDIR /app
# Копируем JAR-файл из образа builder
COPY --from=builder /app/build/libs/creator-tables-json-format-1.0-SNAPSHOT.jar ./
# Запускаем приложение
CMD ["java", "-Dspring.datasource.url=$DB_URL", "-Dspring.datasource.username=$DB_USERNAME", "-Dspring.datasource.password=$DB_PASSWORD", "-jar", "creator-tables-json-format-1.0-SNAPSHOT.jar"]