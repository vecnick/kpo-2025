# Сборка + запуск

Запускать через команду `docker-compose up` из главной директории с файлом docker-compose.yml + если требуется предварительная сборка `docker-compose build`


# WebApi

Проверить через запросы можно на странице общего api `http://localhost:8082/webjars/swagger-ui/index.html` (с переключением между сервисами) или со страничек каждого микросервиса отдельно `http://localhost:8080/swagger-ui/index.html` - payment_service, `http://localhost:8081/swagger-ui/index.html` - order_service

1. Создание аккаунта с 0 балансом: payments - /accounts/create/{userId}
2. Создание заказа на некоторую сумму: orders - /orders/create/{userId}{amount}{description}
3. Проверка балансов пользователей после принятия заказов: payments - /accounts/getAll
4. Проверка статуса заказов (выполнен/отменён) после проверки в сервисе оплаты: orders - /orders/getAll

Примечение:

*баланс может уходить в минус (для упрощения демонстрации)

*отмена заказа происходит, если указать в заказе несуществующий в базе аккаунтов userId


# order_service

Order:
- entity/Order - описание таблицы заказов в базе данных
- enums/OrderStatus - статус заказа
- factory/OrdersFactory - фабрика для создания новых заказов
- repository/OrderRepository - интерфейс для работы с базой данных (таблицей ордеров)
- service/OrderService - создание заказов и взаимодействие с ними
- controller/OrderController - управление заказами через webApi (обращаение к OrderService)

Outbox/Inbox:
- entity/AbstractDelayedTask - описание таблицы запросов к кафке
- entity/OutboxTask - реализация абстрактного класса (таблица запросов для передачи в сервис оплаты)
- enums/DelayedTaskStatus - статус отправляемого запроса (этап отправки)
- enums/DelayedTaskType - тип запроса (какая информация содержится внутри запроса)
- factory/OutboxTaskFactory - создание Outbox запросов на отправку
- repository/OutboxTaskRepository - интерфейс для работы с базой данных (таблицей запросов на отправку)
- service/OutboxTaskService - работа с таблицей запросов на отправку, взаимодействие
- scheduler/OutboxTaskScheduler - планировщик, раз в 5 сек проверяющий наличие неотправленных запросов из таблицы Outbox

- service/InboxTaskOrderConfirmationService - работа с прибывшими потверждениями о заказах (реализация через clever worker без дополнительного сохранения информации из кафки во внутреннюю базу данных)

Kafka:
- config/KafkaPaymentTopic - автосоздание топика в Kafka при запуске сервиса
- kafka/KafkaProducer - отправка запросов на списание счёта на Kafka
- kafka/KafkaOrderConfirmationConsumer - принятие запроса о потверждении заказа от сервиса оплаты с Kafka

Дополнительно:
- utility/JsonDeserializer - сериализция отправляемого запроса из некоторого объекта в json
- utility/JsonDeserializer - десериализция приходящего ответа из json в объект некоторого класса
- utility/Data - получение текущей даты
- records/PaymentRequest - стуктура запроса для отправки информации о заказе
- records/PaymentConfirmationRequest - структура запроса, приходящего в качестве ответа от сервиса оплаты
- config/SwaggerConfig - настрйка Swagger (для создания общего api)


# payment_service

BalanceAccount:
- entity/BalanceAccount  - описание таблицы балансов в базе данных
- enums/BalanceAccountRequestResult - результат операций, связанных с балансом
- factory/BalanceAccountFactory - фабрика для создания новых аккаунтов с 0 балансом
- repository/BalanceAccountRepository - интерфейс для работы с базой данных (таблицей аккаунтов)
- service/PaymentService - создание аккаунтов и взаимодействие с ними
- controller/PaymentController - управление аккаунтами через webApi (обращаение к PaymentService)

Outbox/Inbox:
- entity/AbstractDelayedTask - описание таблицы запросов к кафке
- entity/OutboxTask - реализация абстрактного класса (таблица запросов для передачи результатов оплаты в сервис заказов)
- enums/DelayedTaskStatus - статус отправляемого запроса (этап отправки)
- enums/DelayedTaskType - тип запроса (какая информация содержится внутри запроса)
- factory/OutboxTaskFactory - создание Outbox запросов на отправку
- repository/OutboxTaskRepository - интерфейс для работы с базой данных (таблицей результатов оплаты на отправку)
- service/OutboxTaskService - работа с таблицей запросов с результатами оплаты, взаимодействие
- scheduler/OutboxTaskScheduler - планировщик, раз в 5 сек проверяющий наличие неотправленных запросов из таблицы Outbox

- entity/InboxTask - реализация абстрактного класса (таблица запросов для сохранения входящих сообщений из Kafka)
- factory/InboxTaskFactory - создание Inbox запросов, принятых из Kafka
- repository/InboxTaskRepository - интерфейс для работы с базой данных (таблицей сохраненных сообщений из Kafka)
- service/InboxTaskService - работа с таблицей сообщений, взаимодействие
- scheduler/InboxTaskScheduler - планировщик, раз в 5 сек проверяющий наличие неотправленных запросов из таблицы Outbox
- service/InboxTaskService - работа с прибывшими потверждениями о заказах (реализация через stupid worker c дополнительным сохранением информации, прибевшей из кафки, во внутреннюю базу данных)

Kafka:
- config/KafkaPaymentConfirmationTopic - автосоздание топика в Kafka при запуске сервиса
- kafka/KafkaProducer - отправка запросов на потверждение заказов в Kafka
- kafka/KafkaOrderConsumer - принятие запроса на списание баланса от сервиса заказов с Kafka

Дополнительно:
- utility/JsonDeserializer - сериализция отправляемого запроса из некоторого объекта в json
- utility/JsonDeserializer - десериализция приходящего ответа из json в объект некоторого класса
- utility/Data - получение текущей даты
- records/PaymentRequest - стуктура запроса, полученных от сервиса заказов
- records/PaymentConfirmationRequest - структура запроса, отправляемого сервису заказов для потверждения оплаты
- config/SwaggerConfig - настрйка Swagger (для создания общего api)


# api_getaway

- объединение двух api в одно (port: 8082)


# Схема создания заказа

(ОСНОВНЫЕ процессы, транзации и архитектурные решения)


![async_shopping drawio](https://github.com/user-attachments/assets/c2683883-28e5-423b-8c8f-0a880114e577)


Транзакции (методы, которые имеют аннотацию @Transactional):

- order_service: OrderService - метод createOrder (сохранение заказа + сохранение запроса на списание баланса)
- order_service: OutboxTaskScheduler - метод sendTask (отправка запроса на списание баланса в Kafka + обозначить запрос отправленным)
- payment_service: InboxTaskOrderScheduler - метод taskSubBalance (обозначить запрос отменённым + уменьшить баланс + обозначаем заказ в зависимости от результата списания средств + сохраняем запрос о статусе списания)
- payment_service: OutboxTaskScheduler - метод sendTask (отправляем запрос со статусом потверждения списания в Kafka + обозначаем запрос отправленным)


