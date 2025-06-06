# Определения

- ServiceLocator - убирает жётскую связь между классами, объединяя все объекты в одном месте для получения зависимостей (упрощённого тестирования)
```
class Engine {}
ServiceLocator.register(Engine.class, new Engine());
Engine engine = ServiceLocator.getService(Engine.class);
```

- DI - перенести ответственность за создание экземпляра объекта за пределы класса и передать уже созданный экземпляр объекта обратно.
```
@Service
class Engine {}

@Service
class Car {
    private final Engine engine;

    @Autowired  // Spring сам подставит зависимость
    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

- IoC - управление созданием объектов передаётся внешней системе (Spring), а программист их просто использует
```
@Component
class Engine {}

public class IoCExample {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext("com.example");
        Engine engine = context.getBean(Engine.class);  // IoC Container управляет объектами
    }
}
```

- Singleton - паттерн проектирования, гарантирующий, что в системе существует только 1 объект класса.
```
class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

- Prototype - параметр для Scope в Spring для указания необходимости каждый раз при вызове создавать новый объект
```
@Component
@Scope("prototype") // Новый объект при каждом вызове
class PrototypeBean {}
```

- юнит тесты junit - проверка отдельных узконаправленных модулей, которая происходит без необходимости запуска всей системы
```
@Component
public class CustomerStorage {}

@SpringBootTest
class KpoApplicationTests {

	@Autowired
	private CarService carService;
	
	@Test
	@DisplayName("CarService - тест добавления и получения машины (addCar, takeCar)")
	void test1() {
	    assertEquals(carService.takeCar(customer1), null);
	}
}
	
```

- Mockito (mock + spy) - создание объектов-заглушек (mock - ) и объектов-шпионов (spy - имеет поведение реального объекта, но может записывать определенную информацию о вызове его методов) для передачи в параметры методов тестируемых классов
- @SpringBootTest - аннотация перед написанием тестирующего класса в Spring
```
@SpringBootTest
class KpoApplicationTests {}
```

- @Autowired - внедрение зависимостей в тестирующий класс Spring
```
@Autowired
private CarService carService;
```

- @Component - аннотация перед классом, который будет находится под управлением Spring
```
@Component
public class CustomerStorage {}
```

- + факт: Изачальна все объекты в Spring являются Singleton-ами (до тех пор пока не указать @Scope("prototype"))
