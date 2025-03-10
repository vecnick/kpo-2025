# Занятие 8. DDD

## Цель занятия
- Изучение возможности общения с внешними сервисами, с помощью контролеров.
## Требования к реализации
1. 
## Тестирование
1. 
## Задание на доработку
- 
## Пояснения к реализации
1. Добавьте зависимости в build.gradle
```
   dependencies {
   // Spring Web (включает REST и Tomcat)
   implementation("org.springframework.boot:spring-boot-starter-web")
   // Swagger UI и OpenAPI
   implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
   }
```

```
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HSE Car Service API")
                        .version("1.0")
                        .description("API для управления автомобилями"));
    }
}
```

```
public record CarRequest(
        @Schema(description = "Тип двигателя (PEDAL, HAND, LEVITATION)", example = "PEDAL")
        @Pattern(regexp = "PEDAL|HAND|LEVITATION", message = "Допустимые значения: PEDAL, HAND, LEVITATION")
        String engineType,

        @Schema(description = "Размер педалей (1-15)", example = "6")
        @Min(value = 1, message = "Минимальный размер педалей - 1")
        @Max(value = 15, message = "Максимальный размер педалей - 15")
        @Nullable
        Integer pedalSize
) {}
```

<details> 
<summary>Ссылки</summary>
1. 
</details>