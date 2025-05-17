# Занятие 16. Аутбокс + Redis

## Цель занятия
- Разобраться, что такое паттерн аутбокс, реализовать его, а так же подключить redis.
## Требования к реализации
- Данные в кафку пушатся через таблицу в БД (паттерн outbox)
- Реализован OutboxProcessor
## Тестирование
- При создании покупателя, событие через какое-то время будет отправлено в кафку.
## Задание на доработку
- Добавить кеш к сервису.
## Пояснения к реализации
Большую часть кода для outbox уже добавил в репо, нужно реализовать OutboxProcessor, который будет:
Раз в 5 секунд запрашивать данные из таблицы outbox_events, внутри транзакции пушить их в кафку и менять их состояние
так, чтобы потом еще раз не обрабатывались. 

Для добавления кеша:
Добавить зависимость:
```
implementation("org.springframework.boot:spring-boot-starter-data-redis")
```
и конфиги
```
spring:
  redis:
    host: localhost
    port: 6379
```
```
package hse.kpo.config.redis;

import java.time.Duration;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration(ObjectMapper objectMapper) {
        objectMapper = objectMapper.copy();
        objectMapper = objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        return RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith(cacheName -> cacheName + ":")
            .entryTtl(Duration.ofMinutes(10))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer(objectMapper)));
    }
}
```
Добавить аннотацию над методом поиска машины по вин
```
@Cacheable(value = "cars", key = "#vin")
```
И аннотацию удаления кеша над методами удаления машины и обновления
```
@CacheEvict(value = "cars", key = "#vin")
```
После чего запустить docker compose вместе с редисом.
Проверить, что туда добавились данные можно через терминал докера, или с помощью приложения
Redis Insight, так же для теста, добавьте задержку, при повторном запросе данные должны приходить быстрее:
```
@Transactional
@Cacheable(value = "cars", key = "#vin")
public Optional<Car> findByVin(Integer vin) throws InterruptedException {
Thread.sleep(2000);
return carRepository.findById(vin);
}
```
когда в редис данные записались, проверьте, что при удалении или обновлении покупателя запись в редисе обнуляется
<details> 
<summary>Ссылки</summary>
1. 
</details>