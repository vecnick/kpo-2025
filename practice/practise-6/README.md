# Занятие 6. Поведенческие паттерны
Сегодня посмотрим как можно управлять поведением приложений с помощью различных паттернов.
## Функциональные требования
## Требования к реализации
1. Изменить составление отчета с помощью паттерна Наблюдатель.
## Тестирование
1. Все действия о продажах машин и катамаранов записываются в отчет.
## Задание на доработку
- 
## Пояснения к реализации
Для создания наблюдателей добавьте список в класс, который будем мониторить 
```
final List<SalesObserver> observers = new ArrayList<>();
```

Для добавление наблюдателя создайте метод
```
public void addObserver(SalesObserver observer) {
    observers.add(observer);
}
```

Для реализации оповещений используйте
```
private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
    observers.forEach(obs -> obs.onSale(customer, productType, vin));
}
```

Добавьте метод оповещения в продажу машин
```
notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());
```

Теперь можно не добавлять вручную в отчет информацию о пользователях. 
Но необходимо добавлять и операции. Для этого:

В Gradle добавьте поддержку работы аннотаций
```
implementation("org.springframework.boot:spring-boot-starter-aop")
```

Создайте аннотацию Sales, чтобы можно было не дублировать код в классах
```
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sales {
    String value() default "";
}
```

Каждая аннотация имеет свою реализацию, для этого существует понятие аспект (реализация аннотации):
```
@Component
@Aspect
@RequiredArgsConstructor
public class SalesAspect {
private final SalesObserver salesObserver;

    @Around("@annotation(sales)")
    public Object sales(ProceedingJoinPoint pjp, Sales sales) throws Throwable {

        salesObserver.checkCustomers();

        String operationName = sales.value().isEmpty() ? pjp.getSignature().toShortString() : sales.value();
        try {
            Object result = pjp.proceed();
            salesObserver.checkCustomers();
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }
}
```

```
@Component
@RequiredArgsConstructor
public class ReportSalesObserver implements SalesObserver {
private final CustomerStorage customerStorage;

    private final ReportBuilder reportBuilder = new ReportBuilder();

    public Report buildReport() {
        return reportBuilder.build();
    }

    public void checkCustomers() {
        reportBuilder.addCustomers(customerStorage.getCustomers());
    }

    @Override
    public void onSale(Customer customer, ProductionTypes productType, int vin) {
        String message = String.format(
                "Продажа: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                productType, vin, customer.getName(),
                customer.getHandPower(), customer.getLegPower(), customer.getIq()
        );
        reportBuilder.addOperation(message);
    }
}
```




<details> 
<summary>Ссылки</summary>
1. 
</details>