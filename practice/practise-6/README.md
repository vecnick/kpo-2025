# Занятие 6. Поведенческие паттерны
Сегодня посмотрим как можно управлять поведением приложений с помощью различных паттернов.
## Функциональные требования
## Требования к реализации
1. Изменить составление отчета с помощью паттерна Наблюдатель.
## Тестирование
## Задание на доработку
- 
## Пояснения к реализации
Для создания наблюдателей добавьте private список 
final List<SalesObserver> observers = new ArrayList<>();
Для добавление наблюдателя поставьте метод
public void addObserver(SalesObserver observer) {
observers.add(observer);
}
Для реализации оповещений используйте
    private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }
Добавьте метод оповещения в продажу машин
notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());

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

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sales {
String value() default "";
}

В Gradle добавьте поддержку работы аннотаций
implementation("org.springframework.boot:spring-boot-starter-aop")
<details> 
<summary>Ссылки</summary>
1. 
</details>