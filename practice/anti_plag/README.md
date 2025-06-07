version: '3.8'

volumes:
  payments_postgres_data:
  orders_postgres_data:
  kafka_data:
  zookeeper_data:
  
services:
  orders_db:
    image: postgres:15
    restart: unless-stopped
    environment:
      POSTGRES_DB: orders
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - orders_postgres_data:/var/lib/postgresql/data
  
  zookeeper:   # нужен для Kafka (внешний сервис координации для брокеров)
    image: confluentinc/cp-zookeeper:7.5.0
    restart: unless-stopped
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181   # порт Zookeeper
      ZOOKEEPER_TICK_TIME: 2000   # базовая еденица времени ("tick") для осуществления действий
    ports:
      - "2181:2181"
    volumes:
      - zookeeper_data:/var/lib/zookeeper

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    restart: unless-stopped
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181     # адрес Zookeeper
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092  # адрес Kafka
  	    KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1   # есть всего 1 брокер
    volumes:
      - kafka_data:/var/lib/kafka