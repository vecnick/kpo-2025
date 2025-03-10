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

```
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили", description = "Управление транспортными средствами")
public class CarController {
    private final CarStorage carStorage;
    private final HseCarService carService;
    private final Hse hseFacade;

    @PostMapping
    @Operation(summary = "Создать автомобиль",
            description = "Для PEDAL требуется pedalSize (1-15)")
    public ResponseEntity<Car> createCar(
            @Valid @RequestBody CarRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        var engineType = EngineTypes.find(request.engineType());
        if (engineType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No this type");
        }

        var car = switch (engineType.get()) {
            case EngineTypes.PEDAL -> hseFacade.addPedalCar(request.pedalSize());
            case EngineTypes.HAND -> hseFacade.addHandCar();
            case EngineTypes.LEVITATION -> hseFacade.addLevitationCar();
            default -> throw new RuntimeException();
        };

        carStorage.addExistingCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    private Object getParams(CarRequest request) {
        return switch (request.engineType()) {
            case "PEDAL" -> new PedalEngineParams(
                    Optional.ofNullable(request.pedalSize())
                            .orElseThrow(() -> new IllegalArgumentException("pedalSize обязателен для PEDAL"))
            );
            default -> EmptyEngineParams.DEFAULT;
        };
    }

    @GetMapping
    @Operation(summary = "Получить все автомобили с фильтрацией",
            parameters = {
                    @Parameter(name = "engineType", description = "Фильтр по типу двигателя"),
                    @Parameter(name = "minVin", description = "Минимальный VIN")
            })
    public List<Car> getAllCars(
            @RequestParam(required = false) String engineType,
            @RequestParam(required = false) Integer minVin) {

        return carStorage.getCars().stream()
                .filter(car -> engineType == null || car.getEngineType().equals(engineType))
                .filter(car -> minVin == null || car.getVin() >= minVin)
                .toList();
    }
}
```

```
    /**
     * Добавляет педальный автомобиль в систему.
     *
     * @param pedalSize размер педалей (1-15)
     */
    public Car addPedalCar(int pedalSize) {
        return carStorage.addCar(pedalCarFactory, new PedalEngineParams(pedalSize));
    }

    /**
     * Добавляет автомобиль с ручным приводом.
     */
    public Car addHandCar() {
        return carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    }

    /**
     * Добавляет левитирующий автомобиль.
     */
    public Car addLevitationCar() {
        return carStorage.addCar(levitationCarFactory, EmptyEngineParams.DEFAULT);
    }
```

<details> 
<summary>Ссылки</summary>
1. 
</details>