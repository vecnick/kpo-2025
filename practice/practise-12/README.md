# Занятие 12. Версионирование БД

## Цель занятия
- Научиться работать с версионированием базы данных.
## Требования к реализации
1. Бд и java приложение подняты в докере.
2. Хранение покупателей происходит в контейнере.
3. Связь между покупателями и машинами one-many.
## Тестирование
1. Добавить через свагер или постман сущность, получить информацию о ней в ответ.
## Задание на доработку
1. Добиться работы версионирования
   - В application.yaml поле ddl-auto: none
   - При удалении всех таблиц из бд и старте приложения, они создаются
   - В бд есть таблицы databasechangelog и databasechangeloglock
## Пояснения к реализации
Добавьте репозиторий покупателей, по аналогии с предыдущими.
Для создания связи one-many между покупателем и машинами изменим сущность Customer
```
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int legPower;

    @Column(nullable = false)
    private int handPower;

    @Column(nullable = false)
    private int iq;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catamaran_id")
    private Catamaran catamaran;

    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}
```
А так же добавим поле покупателя в машину
```
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Ссылка на владельца
```
Удалите CustomerStorage и создайте CustomerService
```
package hse.kpo.services;

import java.util.List;
import hse.kpo.domains.Customer;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService implements CustomerProvider {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(CustomerRequest request) {
        var customerOptional = customerRepository.findByName(request.getName());

        if (customerOptional.isPresent()) {
            var customer = customerOptional.get();
            customer.setIq(request.getIq());
            customer.setHandPower(request.getHandPower());
            customer.setLegPower(request.getLegPower());
            return customerRepository.save(customer);
        }
        throw new KpoException(HttpStatus.NOT_FOUND.value(), String.format("no customer with name: %s", request.getName()));
    }

    @Transactional
    @Override
    public boolean deleteCustomer(String name) {
        customerRepository.deleteByName(name); // Добавьте метод в CustomerRepository
        return true;
    }
}
```
Обновите метод продажи машин
```
    @Sales
    public void sellCars() {
        customerProvider.getCustomers().stream()
            .filter(customer -> customer.getCars() == null || customer.getCars().isEmpty())
            .forEach(customer -> {
                Car car = takeCar(customer);
                if (Objects.nonNull(car)) {
                    customer.getCars().add(car); // Добавляем автомобиль в список клиента
                    car.setCustomer(customer);   // Устанавливаем ссылку на клиента в автомобиле
                    carRepository.save(car);     // Сохраняем изменения
                    notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());
                } else {
                    log.warn("No car in CarService");
                }
            });
    }
```
Нужно самостоятельно исправить все остальные ошибки, которые мешают запуску.

Для включения версионирования обновите application.yml:
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
      ddl-auto: none
#      ddl-auto: ${SPRING_JPA_HIBERNATE_DDL_AUTO}
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yaml
server:
  port: 8080

```
и build.gradle
```
plugins {
	id("org.liquibase.gradle") version "2.0.4"
}

dependencies {
	implementation("org.liquibase:liquibase-core")
	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:5.0.0")
```

Для запуска java приложения с бд в докере в папке проекта выполните сборку проекта
```bash
docker-compose build
```
После этого запустите приложение
```bash
docker-compose up
```

Теперь приложение доступно по стандартному порту в браузере
<details> 
<summary>Ссылки</summary>
1. 
</details>