# Занятие 11. Database

## Цель занятия
- Научиться работать с базой данных.
## Требования к реализации
1. Бд поднята в докере и хранение машин происходит в ней.
## Тестирование
1. Добавить через свагер или постман сущность, получить информацию о ней в ответ.
## Задание на доработку
- Доработать хранение сущностей катамаранов и покупателей в бд
## Пояснения к реализации

Скачать docker на ноутбук
Добавьте docker-compose.yml
```
services:
  postgres:
    image: postgres:15-alpine
    container_name: postgres_db
    environment:
      POSTGRES_DB: car_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres -d postgres"]
      interval: 5s
      timeout: 5s
      retries: 5
```
Запустить через терминал в папке проекта
docker-compose up

Добавить зависимость в gradle:
spring-boot-starter-data-jpa — для работы с JPA и Hibernate.
postgresql — драйвер PostgreSQL.
```
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
runtimeOnly("org.postgresql:postgresql")
```
Создание абстрактного класса для двигателей
Для работы с Hibernate создан абстрактный класс AbstractEngine, реализующий интерфейс Engine. 
Это позволяет использовать JPA для маппинга наследования.
```
package hse.kpo.domains;

import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.Engine;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "engine_type")
public class AbstractEngine implements Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "engine_type", insertable = false, updatable = false)
    private String type; // Автоматически заполняется дискриминатором
    

    @Override
    public boolean isCompatible(Customer customer, ProductionTypes type) {
        return false;
    }
}
```
Реализация конкретных двигателей
Нужно, чтобы каждый тип двигателя (PedalEngine, HandEngine, LevitationEngine) стал сущностью, 
унаследованной от AbstractEngine. Пример приведен к PedalEngine
```
@ToString
@Getter
@Entity
@DiscriminatorValue("PEDAL")
@NoArgsConstructor
public class PedalEngine extends AbstractEngine {
    private int size;
```
Настройка связи между Car и Engine
Класс Car преобразован в сущность JPA со связью @OneToOne к AbstractEngine.
Конструктор будет выдавать ошибки, исправьте так, чтобы все было работало.
```
@Getter
@Setter
@Entity
@Table(name = "cars")
@ToString
@NoArgsConstructor
public class Car implements Transport {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vin;
    
    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private AbstractEngine engine;
    
    public Car(int vin, AbstractEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }
    
    public Car(AbstractEngine engine) {
        this.engine = engine;
    }
```
Добавить репозитории Spring Data JPA для доступа к данным, где Car - это сущность,
которая хранится в репозитории, а Integer, ее ключ.
```
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("""
        SELECT c 
        FROM Car c 
        JOIN c.engine e 
        WHERE e.type = :engineType 
        AND c.vin > :minVin
    """)
    List<Car> findCarsByEngineTypeAndVinGreaterThan(
            @Param("engineType") String engineType,
            @Param("minVin") Integer minVin
    );
}
```
В абстрактной фабрике поменять
```
public interface CarFactory<T> {
    Car create(T parameters);
}
```
А так же все реализации на примере PedalCarFactory
```
@Component
public class PedalCarFactory implements CarFactory<PedalEngineParams> {
    @Override
    public Car create(PedalEngineParams carParams) {
        var engine = new PedalEngine(carParams.pedalSize());

        return new Car(engine);
    }
}
```
Удалить Car Storage и внедрить использование CarRepository в HseCarService вместо хранения данных в памяти.
```
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService implements CarProvider{

    private final List<SalesObserver> observers = new ArrayList<>();

    private final CustomerProvider customerProvider;
    private final CarRepository carRepository;

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }

    /**
     * Метод продажи машин
     */
    @Sales
    public void sellCars() {
        var customers = customerProvider.getCustomers();
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = this.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                        notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());
                    } else {
                        log.warn("No car in CarService");
                    }
                });
    }

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = carRepository.findAll().stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(carRepository::delete);

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Car} в систему.
     *
     * @param carFactory фабрика для создания автомобилей
     * @param carParams параметры для создания автомобиля
     */
    public <T> Car addCar(CarFactory<T> carFactory, T carParams) {
        return carRepository.save(carFactory.create(carParams));
    }

    public Car addExistingCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> findByVin(Integer vin) {
        return carRepository.findById(vin);
    }

    public void deleteByVin(Integer vin) {
        carRepository.deleteById(vin);
    }

    public List<Car> getCarsWithFiltration(String engineType, Integer vin) {
        return carRepository.findCarsByEngineTypeAndVinGreaterThan(engineType, vin);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }
}
```
Измените контроллер
```

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Tag(name = "Автомобили", description = "Управление транспортными средствами")
public class CarController {
    private final HseCarService carService;
    private final Hse hseFacade;

    @GetMapping("/{vin}")
    @Operation(summary = "Получить автомобиль по VIN")
    public ResponseEntity<Car> getCarByVin(@PathVariable int vin) {
        return carService.findByVin(vin)
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
    public ResponseEntity<Object> sellCar(@PathVariable int vin) {
        return carService.findByVin(vin).map(car -> {
            carService.deleteByVin(car.getVin());
            hseFacade.sell();
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{vin}")
    @Operation(summary = "Обновить автомобиль")
    public ResponseEntity<Car> updateCar(
            @PathVariable int vin,
            @Valid @RequestBody CarRequest request) {

        return carService.findByVin(vin)
                .map(existingCar -> {
                    existingCar.setEngine(createEngineFromRequest(request));
                    carService.addExistingCar(existingCar);
                    return ResponseEntity.ok(existingCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{vin}")
    @Operation(summary = "Удалить автомобиль")
    public ResponseEntity<Void> deleteCar(@PathVariable int vin) {
        carService.deleteByVin(vin);
        return ResponseEntity.ok().build();
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

        return carService.getCarsWithFiltration(engineType, minVin);
    }

    private AbstractEngine createEngineFromRequest(CarRequest request) {
        return switch (EngineTypes.valueOf(request.engineType())) {
            case PEDAL -> new PedalEngine(request.pedalSize());
            case HAND -> new HandEngine();
            case LEVITATION -> new LevitationEngine();
        };
    }
}
```
Добавьте конфигурацию в application.yml
```
spring:
  application:
    name: kpo-app
  datasource:
    url: jdbc:postgresql://localhost:5432/car_db
    username: postgres
    password: postgres
#    url: ${SPRING_DATASOURCE_URL}
#    username: ${SPRING_DATASOURCE_USERNAME}
#    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
#      ddl-auto: ${SPRING_JPA_HIBERNATE_DDL_AUTO}
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
server:
  port: 8080
```

<details> 
<summary>Ссылки</summary>
1. 
</details>