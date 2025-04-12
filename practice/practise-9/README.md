# Занятие 9. Controllers

## Цель занятия
- Изучение возможности общения с внешними сервисами, с помощью контролеров.
## Требования к реализации
1. Добавить возможность работы с машинами (crud + продажа) с помощью restController.
2. Добавить валидацию запросов.
3. Добавить возможность просмотра через swagger.
## Тестирование
1. Отправить в swagger запросы на crud операции машины + продажу.
## Задание на доработку
- Добавить контроллеры для катамаранов, покупателей, отчетов работы системы/транспорта
## Пояснения к реализации
Добавьте зависимости в build.gradle
```
   dependencies {
   // Spring Web (включает REST и Tomcat)
   implementation("org.springframework.boot:spring-boot-starter-web")
   // Swagger UI и OpenAPI
   implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
   }
```

В папке config внутри hse.kpo создать конфигурацию Swagger
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
public enum EngineTypes {
HAND ("HAND"),
PEDAL ("PEDAL"),
LEVITATION ("LEVITATION");

    private final String name;

    EngineTypes(String name) {
        this.name = name;
    }

    public static Optional<EngineTypes> find(String name) {
        return Arrays.stream(values()).filter(type -> type.name.equals(name)).findFirst();
    }
}
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

    // GET by VIN
    @GetMapping("/{vin}")
    @Operation(summary = "Получить автомобиль по VIN")
    public ResponseEntity<Car> getCarByVin(@PathVariable int vin) {
        return carStorage.getCars().stream()
                .filter(car -> car.getVin() == vin)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продать все доступные автомобили")
    public ResponseEntity<Void> sellAllCars() {
        carService.sellCars();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell/{vin}")
    @Operation(summary = "Продать автомобиль по VIN")
    public ResponseEntity<Void> sellCar(@PathVariable int vin) {
        var carOptional = carStorage.getCars().stream()
                .filter(c -> c.getVin() == vin)
                .findFirst();

        if (carOptional.isPresent()) {
            var car = carOptional.get();
            carStorage.getCars().remove(car);
            // Логика продажи (упрощенно)
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить автомобиль")
    public ResponseEntity<Car> updateCar(
            @PathVariable int vin,
            @Valid @RequestBody CarRequest request) {

        return carStorage.getCars().stream()
                .filter(car -> car.getVin() == vin)
                .findFirst()
                .map(existingCar -> {
                    var updatedCar = createCarFromRequest(request, vin);
                    carStorage.getCars().remove(existingCar);
                    carStorage.addExistingCar(updatedCar);
                    return ResponseEntity.ok(updatedCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить автомобиль")
    public ResponseEntity<Void> deleteCar(@PathVariable int vin) {
        boolean removed = carStorage.getCars().removeIf(car -> car.getVin() == vin);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
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

    private Car createCarFromRequest(CarRequest request, int vin) {
        Engine engine = switch (EngineTypes.valueOf(request.engineType())) {
            case PEDAL -> new PedalEngine(request.pedalSize());
            case HAND -> new HandEngine();
            case LEVITATION -> new LevitationEngine();
        };
        return new Car(vin, engine);
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
1. https://proglib.io/p/chto-takoe-api-i-crud-prostymi-slovami
2. https://javarush.com/groups/posts/3160-spring---ehto-ne-strashno-kontroliruem-svoy-rest
3. https://habr.com/ru/articles/541592/
4. https://www.postman.com/
5. http://localhost:8080/swagger-ui/index.html#/
</details>