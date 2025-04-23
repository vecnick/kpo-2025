    
# Сборка и запуск

Сборка
```
./gradlew build
```

Запуск
```
java -jar build/libs/zoo_erp_swagger-1.0.0.jar
```

- или через функцию main класса Main

Web-api
```
http://localhost:8080/swagger-ui/index.html
```

- или через функцию main класса Main


# Структура

- Основные классы доменной модели: /domain/models

- Фабрики которые их создают (+ увеличивают счётчик id): /domain/factories

- In-Memory хранилища созданных объектов: /infrastructure/storages

- Необходимые сервисы для работы над объектами: /application/services

- Формат ответов сервера, которые отдаются клиенту по web-api: /presentation/responses

- Реализация взаимодействия с web-api (модули, содержащие запросы к серверу): /presentation/controllers

# Clean Architecture

- связи между модулями по интерфейсам

- объекты передаются через конструктор, а не вшиты в класс

- Domain полностью изолирован от внешних модулей

# DDD

- Value Objects: ответы сервера /presentation/responses и доменные события /domain/events

- Инкапсуляция бизнес-правил внутри доменных объектов: canEat() в типах животных /domain/models/animalType (еда которую ест животное, зависит от его типа), логики состояния объектов в самих доменах: isSick(), isHungry(), isDirty()... в /domain/models

# Запросы

- "GET/модель" - получить список всех созданных моеделй

- "POST/модель" - создание объекта

- "DELETE/модель" - удаление объекта

- прочие запросы дополнительно уточнены

