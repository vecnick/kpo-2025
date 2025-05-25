# Определения

- @Bean - даёт контроль Spring-у над отдельным методом в классе. Можно указать только для метода, который создаёт и возвращает объект класса. Позволяет делать @Autowired на отдельные методы класса (тестировать и внедрять методы), а не на весь класс (как с @Component)

```
@Autowired
private MathService mathService;

@Test
void testAdd() {
    int result = mathService.add(3, 5);
    assertEquals(8, result);
}
```

- @Configuration - нужен для указания над классом, которое сообщает о наличии @Bean в нём.

```
@Configuration
public class AppConfig {

    @Bean
    public MathService mathService() {
        return new MathService();
    }
}
```

- Jacoco (Java сode сoverage) - инструмент для проверки покрытия тестов в java

```
// добавить в gradle build
plugins {
    java
    jacoco
}
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // Сначала запускаем тесты, потом создаём отчёт
}

// запустить проверку покрытия
./gradlew clean test jacocoTestReport

// посмотреть покрытие
build/reports/jacoco/test/html/index.html
```


- Checkstyle - инструмент для проверки стиля кода в Java

```
// добавить в gradle build
plugins {
    java
    checkstyle
}
checkstyle {
    toolVersion = "10.13.0"
    isIgnoreFailures = false // не прерывает сборку при ошибках
    maxWarnings = 0          // запрещает предупреждения
    maxErrors = 0            // запрещает ошибки
}

// запустить проверку стиля main и test файлов
./gradlew checkstyleMain checkstyleTest

// посмотреть результаты
build/reports/checkstyle/main.html 
build/reports/checkstyle/test.html 
```

- application.yml - конфигурационный файл в проектах Spring Boot, позволяет хранить настройки приложения и имзенять параметры без редактирования кода

```
// application.yml
spring:
  datasource:
    url: jdbc:mysql://prod-db:3306/myapp
    username: prod_user
    password: prodpass
    
// java:
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
}
```

- +факт: 100% покрытие кода тестами не всегда хорошо, так как важнее тестировать критически важную логику, а не просто увеличивать число покрытых строк.
