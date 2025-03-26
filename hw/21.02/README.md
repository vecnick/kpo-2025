# Определения

- Поведенческие паттерны -  это шаблоны проектирования, которые определяют как объекты взаимодействуют между собой и каким образом распределяется ответственность между ними.

- Цепочка обязанностей (Chain of responsibility) - позволяет передавать запрос по цепочке обработчиков, где каждый обработчик решает, обработать ли запрос или передать дальше (“обработай сам или перешли другому”) ((позволяет разделить логику и избавиться от громоздких if-else))
```
// Оператор (самые простые запросы)
class Operator extends ComplaintHandler {
    public void handleComplaint(String issue, int severity) {
        if (severity <= 1) {
            System.out.println("Оператор решил проблему: " + issue);
        } else if (nextHandler != null) {
            nextHandler.handleComplaint(issue, severity);
        }
    }
}

// Менеджер (более сложные вопросы)
class Manager extends ComplaintHandler {
    public void handleComplaint(String issue, int severity) {
        if (severity <= 3) {
            System.out.println("Менеджер решил проблему: " + issue);
        } else if (nextHandler != null) {
            nextHandler.handleComplaint(issue, severity);
        }
    }
}

// Директор (самые сложные вопросы)
class Director extends ComplaintHandler {
    public void handleComplaint(String issue, int severity) {
        System.out.println("Директор решил проблему: " + issue);
    }
}
```

- Команда (Command) - выполняет команду за пользователя (передаётся ссылка на объект и параметры, а функция подставляет всё необходимое и выполняет команду за пользователя)
```
// Команда выключения света (Пульт не знает о лампе, он просто выполняет команды)
class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOff();
    }
}
```
 
- Интерпретатор (Interpreter) - позволяет определять грамматику и интерпретировать простые языки или команды.
```
// Константа (число)
class Number implements Expression {
    private int value;
    public Number(int value);
    public int interpret();
}

// Сложение
class Addition implements Expression {
    private Expression left, right;
    public Addition(Expression left, Expression right);
    public int interpret();
}

// Вычитание
class Subtraction implements Expression {
    private Expression left, right;
    public Subtraction(Expression left, Expression right);
    public int interpret();
}

// Использование (интерпретация выражения 5 + (2 - 1))
public class Main {
    public static void main(String[] args) {
        Expression five = new Number(5);
        Expression two = new Number(2);
        Expression one = new Number(1);

        Expression subtraction = new Subtraction(two, one); // 2 - 1
        Expression addition = new Addition(five, subtraction); // 5 + (2 - 1)

        System.out.println("Результат: " + addition.interpret()); // 5 + 1 = 6
    }
}

```

- Итератор (Iterator) - объект, позволяющий получить доступ к элементам объекта-агрегата независимо от его реализации
```
interface Iterator<T> {
    boolean hasNext();
    T next();
}

class LibraryIterator implements Iterator<Book> {
    private List<Book> books;
    private int position = 0;

    public LibraryIterator(List<Book> books) {
        this.books = books;
    }

    public boolean hasNext() {
        return position < books.size();
    }

    public Book next() {
        return hasNext() ? books.get(position++) : null;
    }
}
```

- Посредник (Mediator) - избавляет объекты классов от необходимости ссылаться друг на друга с целью выполнения некоторых действий, обеспечивая слабую связь между ними (образаются друг к другу не напрямую, а через команды посредника) ((определяет как между собой взаимодействуют объекты))
```
// Машины договариваются между друг другом через светофор
class TrafficLight implements TrafficMediator {
    private List<Car> cars = new ArrayList<>();
    private boolean isGreen = false;

    public void registerCar(Car car) {
        cars.add(car);
    }

    public void setGreenLight() {
        isGreen = true;
        notifyCars(null, "go");
    }

    public void setRedLight() {
        isGreen = false;
        notifyCars(null, "stop");
    }

    public void notifyCars(Car car, String action) {
        for (Car c : cars) {
            if (!c.equals(car)) {
                c.receiveSignal(action);
            }
        }
    }
}
```

- Хранитель (Memento) - поведенческий шаблон проектирования, позволяющий не нарушая инкапсуляцию зафиксировать и сохранить внутреннее состояние объекта так, чтобы позднее восстановить его в это состояние. 
```
// Хранитель (хранит в себе текст для возможного будущего восстановления)
class TextMemento {
    private final String text;

    public TextMemento(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
```

- Наблюдатель (Observer) - механизм, который позволяет следить за состоянием объекта и уведомлять об его изменении
```
class NewsChannel implements NewsPublisher {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(String news) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(news);
        }
    }
}
```

- Состояние (State) - объект сам выбирает своё поведение в зависимости от его внутреннего состояния (классы-состояния, которые реализуют одни и те же методы по-разному)
```

// Состояние 1
class ReadyState implements ATMState {
    public void insertCard() { System.out.println("Карта уже вставлена."); }
    public void ejectCard() { System.out.println("Карта извлечена."); }
    public void withdrawMoney() { 
        atm.decreaseCash(100);
        System.out.println("Деньги выданы.");
    }
}

// Состояние 2 (с другой реализацией тех же комманд)
class NoCashState implements ATMState {
    public void insertCard() { System.out.println("Банкомат пуст, нельзя вставить карту."); }
    public void ejectCard() { System.out.println("Карты нет."); }
    public void withdrawMoney() { System.out.println("Нет денег в банкомате."); }
}

// Класс, который хранит в себе состояния и вызывает их методы
class ATM {
    private ATMState state;
    int cash = 200;
    
    public ATM() { this.state = new ReadyState(); } // Начальное состояние

    public void setState(ATMState state) {
        this.state = state;
    }

    public void insertCard() { state.insertCard(); }
    public void ejectCard() { state.ejectCard(); }
    public void withdrawMoney() { state.withdrawMoney(); }
    
    public void decreaseCash(int amount) {
        cash -= amount;
        if (cash <= 0) {
            System.out.println("Деньги закончились! Меняем состояние.");
            setState(new NoCashState()); // БАНКОМАТ САМ меняет своё состояние
        }
    }
}

// Использование
public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.insertCard();  // Карта вставлена.
        atm.withdrawMoney(); // Деньги выданы.
        atm.withdrawMoney(); // Деньги выданы.

        // Деньги кончились, банкомат САМ поменял состояние
        atm.insertCard();  // Нет денег! Карта не принимается.
    }
}
```

- Стратегия (Strategy) - клиент решает, каким поведением будет сейчас обладать объект (классы-состояния, которые реализуют одни и те же методы по-разному)
```

// Конкретные стратегии
class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Оплачено картой: $" + amount); }
}

class CashPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Оплачено наличными: $" + amount); }
}

class CryptoPayment implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Оплачено криптовалютой: $" + amount); }
}

// Контекст (магазин)
class Checkout {
    private PaymentStrategy strategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(int amount) {
        strategy.pay(amount);
    }
}

// Использование
public class Main {
    public static void main(String[] args) {
        Checkout checkout = new Checkout();

        checkout.setPaymentStrategy(new CreditCardPayment()); // пользователь выбирает стратегию
        checkout.processPayment(100);  // Оплачено картой: $100

        checkout.setPaymentStrategy(new CashPayment()); // пользователь выбирает стратегию
        checkout.processPayment(50);  // 💵 Оплачено наличными: $50
    }
}

```

- Шаблонный метод (Template method) - шаблонный метод задаёт структуру алгоритма, но конкретные шаги можно менять в подклассах. (использовать когда алгоритм одинаковый, но некоторые шаги могут различаться)

```
// Абстрактный класс с шаблонным методом
abstract class Beverage {
    // Шаблонный метод (заданный алгоритм)
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourIntoCup();
        if (customerWantsExtras()) { // Хук (hook) - можно переопределить
            addExtras();
        }
    }

    private void boilWater();
    private void pourIntoCup();
    
    // Абстрактные методы, которые можно переопределить
    protected abstract void brew();
    protected abstract void addExtras();

    // Хук: можно переопределить, чтобы отказаться от добавок
    protected boolean customerWantsExtras() {
        return true;
    }
}

class Tea extends Beverage {
    // Даём свою реализацию абстрактным методам
    protected void brew();
    protected void addExtras();
}

class Coffee extends Beverage {
    // Даём свою реализацию абстрактным методам
    protected void brew();
    protected void addExtras();

    // Переопределяем хук: некоторые люди не хотят добавки
    protected boolean customerWantsExtras() {
        return false; // Не добавлять молоко и сахар
    }
}
``` 

- Посетитель (Visitor) - позволяет добавить новый операции к объектам некого класса, не изменяя его (выполняет операции над объектами других классов)

```
class Vet implements AnimalVisitor {
    public void visit(Lion lion);
    public void visit(Monkey monkey);
    public void visit(Snake snake);
}
```

- +1 факт - Итератор скрыт в for-each в Java – можно перебирать элементы массива, не зная, как он устроен внутри.

