# Занятие 4. Порождающие паттерны
Генеративные паттерны позволяют создавать объекты в зависимости от контекста.
В предыдущих занятиях мы уже использовали некоторые генеративные паттерны, например, `Factory Method` внутри 
`Absatract Factory`при создании автомобилей.
В данном занятии мы рассмотрим реализацию паттерна `Builder` на примере задачи построения отчета о работе нашей системы.
Так же добавим новый тип объектов - катамараны, для дальнейшей работы.
## Функциональные требования
1. Добавить возможность составления отчета о работе системы.
2. Добавить возможность продажи катамаранов.
3. Для катамаранов уменьшить требования силы покупателя до 2 в руках/ногах + до 200 в iq.
## Требования к реализации
1. Реализовать катамараны с помощью интерфейсов и дженериков аналогично с машинам.
2. Отчет должен составляться с помощью паттерна билдера
3. Реализовать проверку типа совместимости силы с помощью enum.
4. Добавить пробросы ошибок + логирование (минимум в 3 местах)
## Тестирование
## Задание на доработку
- Добавьте в `ReportBuilder` возможность вывода текущего содержимого склада автомобилей и кораблей.
## Пояснения к реализации
Для начала заведем класс `Report`, который будет представлять собой отчет о работе нашей системы.
```
public record Report(String title, String content) {
    @Override
    public String toString() {
        return String.format("%s\n\n%s", title, content);
    }
}
```
В качестве заголовка отчета мы будем использовать слово "Отчет" и текущую дату, а в качестве содержания - содержимое хранилища покупателей и список проделанных операций.
Также перегрузим метод `ToString` для вывода отчета в консоль.
Для построения отчета мы будем использовать паттерн `Builder`. Для его реализации создадим класс `ReportBuilder`.
```
public class ReportBuilder
{
}
```
Добавим в класс `ReportBuilder` поле для хранения содержимого отчета. Для этого заведем поле `content` типа `StringBuilder` и проинициализируем его.

Класс `StringBuilder` представляет собой реализацию паттерна `Builder` для строк.
Добавим в наш класс метод `addCustomers`, который будет добавлять в отчет содержимое хранилища покупателей.
```
    public ReportBuilder addCustomers(List<Customer> customers)
    {
        content.append("Покупатели:");
        customers.forEach(customer -> content.append(String.format(" - %s", customer)));
        content.append("\n");

        return this;
    }
```
Обратите внимание, что метод `addCustomers` возвращает `this`, что позволяет использовать цепочечные вызовы методов, что характерно для паттерна `Builder`.
Теперь добавим метод `addOperation`, который будет добавлять в отчет проделанную операцию.
```
    public ReportBuilder addOperation(String operation)
    {
        content.append(String.format("Операция: %s", operation));
        content.append(System.lineSeparator());
        return this;
    }
```
Теперь добавим метод `build`, который будет возвращать построенный отчет.
```
    public Report build()
    {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }
```
Теперь мы можем использовать класс `ReportBuilder` для построения отчета.
```
		var reportBuilder = new ReportBuilder()
				.addOperation("Инициализация системы")
				.addCustomers(customerStorage.getCustomers());

		hseCarService.sellCars();

		var report = reportBuilder
				.addOperation("Продажа автомобилей")
				.addCustomers(customerStorage.getCustomers())
				.build();

		System.out.println(report.toString());
```
Как мы видим, код стал проще и понятнее.


Enum должен создаться с двумя типами
```
public enum ProductionTypes {
    CAR,
    CATAMARAN
}
```
Кастомизируем проверку совместимости относительно типу продукции
```
public boolean isCompatible(Customer customer, ProductionTypes type) {
    return switch (type) {
        case ProductionTypes.CAR -> customer.getHandPower() > 5;
        case ProductionTypes.CATAMARAN -> customer.getHandPower() > 2;
        case null, default -> throw new RuntimeException("This type of production doesn't exist");
};
```

Для логирования добавляем аннотацию @Slf4j из семейства ломбок и пишем в коде 
log.<уровень логов - warn,error,debug>("log text");
<details> 
<summary>Ссылки</summary>
1. 
</details>