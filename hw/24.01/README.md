# Определения

- KISS (Keep It Simple, Stupid) - не усложнять код, делая читаемость кода и его поддержку более простой; использовать готовые функции, вместо написания своих;

```
/* no KISS */
public class MaxNumIdentifier {
    public int max(int a, int b) {
        if (a > b) {
            return a;
        }
        else {
            return b;
        }
    }
}

public static class Main {
    public void Main(String[] args) {
        System.out.println(max(10, 20));
    }
}

/* KISS */
public static class Main {
    public void Main(String[] args) {
        System.out.println(Math.max(a, b));
    }
}
```

- DRY (Don’t Repeat Yourself) - выносить повторяющийся код в отдельную реализацию, чтобы при необходимости изменить код не изменять каждую из частей отдельно
 
```
/* No DRY */
public class DiscountCalculator {
    public double calculateRegularDiscount(double price) {
        return price * 0.10; // 10% скидка
    }

    public double calculateHolidayDiscount(double price) {
        return price * 0.15; // 15% скидка
    }
}

/* DRY */
public class DiscountCalculator {
    public double calculateDiscount(double price, double discountRate) {
        return price * discountRate;
    }
}
```

- YAGNI (You Aren’t Gonna Need It) - не оставлять и не использовать код, который не нужен, удалять всё лишнее.

```
/* No YAGNI */
public class Square {
    private int a_;
    private int b_;
    
    public Square(int a) {
        a_ = a;
    }
    public int getSquare() {
        return a_ * a_;
    }
    public int getDoubleSquare() {
        return a_ * a_ * b_ * b_;
    }
}

/* YAGNI */
public class Square {
    private int a_;
    public Square(int a) {
        a_ = a;
    }
    public int getSquare() {
        return a_;
    }
}
```

- BDUF (Big Design Up Front) - перед началом написания кода продумать архитектуру проекта; у кода должна быть чёткая структура с определениями сущностей в отведённых для них местах, не скапливающихся в одном месте.

```
/* No BDUF */
public class Main {
    public static void main(String[] args) {
        // Прямое создание объектов без абстракции
        String name = "Alice";
        int age = 25;
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
    }
}

/* BDUF */
class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        User user = new User("Alice", 25);
        user.displayInfo();
    }
}

```

- SOLID
 - Single-responsibility principle (Принцип единственной ответственности) - класс должен иметь только одну причину для изменения; класс должен отвечать только за что-то одно
```
/* No Single-responsibility principlo */
class Report {
    void calculate() {}
    void print() {}
}

/* Single-responsibility principl */
class ReportCalculator {
    void calculate() {}
}

class ReportPrinter {
    void print() {}
}
```
 
 - Open–closed principle (Принцип открытости-закрытости) - объекты должны быть открыты для расширения, но закрыты для модификации.
```
/* No Open–closed principle */
class DiscountCalculator {
    double calculateDiscount(String type, double price) {
        if (type.equals("NEW_YEAR")) return price * 0.9;
        else if (type.equals("BLACK_FRIDAY")) return price * 0.8;
        return price;
    }
}

/* Open–closed principle */
interface Discount {
    double apply(double price);
}
class NewYearDiscount implements Discount {
    public double apply(double price) { return price * 0.9; }
}
class BlackFridayDiscount implements Discount {
    public double apply(double price) { return price * 0.8; }
}

class DiscountCalculator {
    double calculateDiscount(Discount discount, double price) {
        return discount.apply(price);
    }
}
\
```
 
 - Liskov substitution principle (Принцип подстановки Лисков) - классы наследники должны полностью уметь заменять своих родителей.
```
/* No Liskov substitution principle */
class Rectangle {
    int width, height;
    void setWidth(int w) { this.width = w; }
    void setHeight(int h) { this.height = h; }
}

class Square extends Rectangle {
    void setWidth(int w) { super.setWidth(w); super.setHeight(w); }
    void setHeight(int h) { super.setWidth(h); super.setHeight(h); }
}

/* Liskov substitution principle */
class Rectangle {
    int width, height;
    Rectangle(int w, int h) { this.width = w; this.height = h; }
}

class Square {
    int side;
    Square(int side) { this.side = side; }
}
```
 
 - Interface segregation principle (Принцип разделения интерфейсов) - разделять интерфейсы, не заставлять классы реализовывать методы, которые они не используют 
```
/* No Interface segregation principle */
interface Machine {
    void print();
    void scan();
    void fax();
}
class Printer implements Machine {
    public void print() {}
    public void scan() {}
    public void fax() {}
}

/* Interface segregation principle */
interface IPrinter { void print(); }
interface IScanner { void scan(); }
interface IFax { void fax(); }

class Printer implements IPrinter {
    public void print() {}
}

```
 
 - Dependency inversion principle (Принцип инверсии зависимостей) -  зависимости должны строиться на основе абстракций, а не конкретных классов
```
/* No Dependency inversion principle */
class MySQLDatabase {}
class MyDataBase {
    private Database db = new MySQLDatabase();
}

/* Dependency inversion principle */
interface Database {}
class MySQLDatabase1 implements Database {}
class MySQLDatabase2 implements Database {}

class MyDataBase {
    private Database db;
    MyDataBase(Database db) { this.db = db; }
}
```
 
- APO (Avoid Premature Optimization) - избегать преждевременных оптимизаций, которая может сильно усложнить код или увеличить время разработки, не привнеся ощутимых результатов

```
/* No APO */
class SortFiveNumsFast {
    private void swap(int[] array, int ind1, int ind2) {
        int buff = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = buff;
    }
    private void merge(int[] array, int left, int mid, int right) {
        int leftPartInd = 0;
        int rightPartInd = 0;
        int maxLen = right - left + 1;
        int[] newArray = new int[maxLen];;

        while (leftPartInd + rightPartInd < maxLen) {
            if ((mid + 1 + rightPartInd > right) || (left + leftPartInd <= mid) && (array[left + leftPartInd] < array[mid + 1 + rightPartInd])) {
                newArray[leftPartInd + rightPartInd] = array[left + leftPartInd];
                leftPartInd++;
            }
            else {
                newArray[leftPartInd + rightPartInd] = array[mid + 1 + rightPartInd];
                rightPartInd++;
            }
        }

        for (int i = 0; i < maxLen; ++i) {
            array[left + i] = newArray[i];
        }
    }
    private void sort_recursive(int[] array, int left, int right) {
        if (right - left <= 1) {
            if (array[left] > array[right]) {
                swap(array, left, right);
            }
            return;
        }

        int mid = (left + right) / 2;
        sort_recursive(array, left, mid);
        sort_recursive(array, mid + 1, right);

        merge(array, left, mid, right);
    }
    public void sort(int[] array) {
        sort_recursive(array, 0, array.length - 1);
    }
}

public class Main {
    public static void main(String[] args) {
        int[] numbers = {10, 10, 5, 2, 5};
        SortFiveNumsFast sortFast = new SortFiveNumsFast();
        sortFast.sort(numbers);
        for (int i = 0; i < 5; ++i) {
            System.out.println(numbers[i]);
        }
    }
}

/* APO */
class SortFiveNumsSlow {
    public void sort(int[] nums) {

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                if (nums[i] > nums[j]) {
                    int buf = nums[i];s
                    nums[i] = nums[j];
                    nums[j] = buf;
                }
            }
        }
    }
}
```
- Бритва Оккама - не создавать ненужных сущностей и стремиться к сокращению усложнений

```
/* No Бритва Оккама */
public class DataBase {
    private String name_;
    public void setName(String name) {}
}
public class DataBaseWithTags extends DataBase {
    private String tags_;
    public void setTags(String tags) {}
}
public class DataBaseWithTagsAndParams extends DataBaseWithTags {
    private String params_;
    public void setParams(String params) {}
}
public class MyDataBase extends DataBaseWithTagsAndParams {}

/* Бритва Оккама */
public class MyDataBase {
    private String name_;
    private String tags_;
    private String params_;
    public void setName(String name) {}
    public void setTags(String tags) {}
    public void setParams(String params) {}
}
```

- Stream-api - инструмент для потоков, нужен для обработки коллекций в функциональном стиле. Не изменяет оригинальную коллекцию и позволяет заменить for циклы. Имеет следующие методы:

```
List<String> words = List.of("apple", "banana", "cherry", "apple", "avocado");

// Промежуточные методы (Intermediate)
words.stream().filter(word -> word.startsWith("a")).forEach(System.out::println); // Оставляет только слова на "a"
words.stream().map(String::toUpperCase).forEach(System.out::println); // Преобразует в верхний регистр
words.stream().flatMap(word -> Arrays.stream(word.split(""))).forEach(System.out::print); // Разбивает строки на символы
words.stream().distinct().forEach(System.out::println); // Убирает дубликаты
words.stream().sorted().forEach(System.out::println); // Сортирует строки по алфавиту
words.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println); // Обратная сортировка
words.stream().peek(System.out::println).collect(Collectors.toList()); // Выводит элементы для отладки
words.stream().limit(2).forEach(System.out::println); // Ограничивает поток до 2 элементов
words.stream().skip(2).forEach(System.out::println); // Пропускает первые 2 элемента

// Терминальные методы (Terminal)
long count = words.stream().count(); // Подсчитывает количество элементов
Optional<String> first = words.stream().findFirst(); // Возвращает первый элемент (Optional)
Optional<String> any = words.stream().findAny(); // Возвращает любой элемент (Optional)
boolean allMatch = words.stream().allMatch(word -> word.length() > 3); // Проверяет, соответствуют ли все элементы условию
boolean anyMatch = words.stream().anyMatch(word -> word.startsWith("b")); // Проверяет, есть ли хотя бы один элемент, начинающийся на "b"
boolean noneMatch = words.stream().noneMatch(word -> word.equals("grape")); // Проверяет, что ни один элемент не равен "grape"

// Агрегация
Optional<Integer> reducedSum = Stream.of(1, 2, 3, 4, 5).reduce(Integer::sum); // Суммирует элементы
int reducedWithIdentity = Stream.of(1, 2, 3, 4, 5).reduce(0, Integer::sum); // Суммирует элементы с начальным значением

// Сбор в коллекции
List<String> list = words.stream().collect(Collectors.toList()); // Собирает элементы в список
Set<String> set = words.stream().collect(Collectors.toSet()); // Собирает элементы в множество
Map<Character, List<String>> grouped = words.stream().collect(Collectors.groupingBy(word -> word.charAt(0))); // Группирует по первой букве
Map<Boolean, List<String>> partitioned = words.stream().collect(Collectors.partitioningBy(word -> word.length() > 5)); // Разделяет на 2 группы
String joined = words.stream().collect(Collectors.joining(", ")); // Объединяет элементы в строку

// Числовые потоки
IntStream.range(1, 5).forEach(System.out::println); // Создает поток чисел от 1 до 4
IntStream.rangeClosed(1, 5).forEach(System.out::println); // Создает поток чисел от 1 до 5
int sum = IntStream.of(1, 2, 3, 4, 5).sum(); // Суммирует элементы потока
double average = IntStream.of(1, 2, 3, 4, 5).average().orElse(0); // Вычисляет среднее значение
int max = IntStream.of(1, 2, 3, 4, 5).max().orElse(0); // Находит максимальное число
int min = IntStream.of(1, 2, 3, 4, 5).min().orElse(0); // Находит минимальное число

// Параллельные потоки
words.parallelStream().forEach(System.out::println); // Использует многопоточный стрим

```


- +1 уникальный факт связанный с темами выше или семинаром

 -В гибких проектах (с методологией разработки Agile, Scrum) BDUF считается плохой практикой (пример: веб-разработка), но в критически важных проектах (разработка по Waterfall) он часто используется (пример: авиасистемы, банки)


