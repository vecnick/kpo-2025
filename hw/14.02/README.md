# Определения

- Порождающие паттерны - это шаблон проектирования, который упрощает создание объектов в коде. Главная задача - отделить процесс создания объекта от его использования

- Singleton (Одиночка) - паттерн проектирования, который гарантирует, что у класса есть только один экземпляр
```
public class Singleton {
    private static Singleton instance;

    private Singleton() {} // приватный конструктор

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

- Factory (Фабрика) - вынос создания объекта в отдельный метод

- Fabric method (Фабричный метод) - наследование от главного класса фабрики. Происходит создание одного объекта нужного класса.

```
abstract class CarFactory {
    abstract Car createCar();
}

class TeslaFactory extends CarFactory {
    public Car createCar() { return new Tesla(); }
}

class BMWFactory extends CarFactory {
    public Car createCar() { return new BMW(); }
}
```

- Abstract Factory (Абстрактная фабрика) - имплементация интерфейса фабрик. Происходит создание нескольких объектов, связанных между собой

```
interface CarFactory {
    Car createCar();
    Engine createEngine();
}

class TeslaFactory implements CarFactory {
    public Car createCar() { return new Tesla(); }
    public Engine createEngine() { return new TeslaEngine(); }
}

class BMWFactory implements CarFactory {
    public Car createCar() { return new BMW(); }
    public Engine createEngine() { return new BMWEngine(); }
}
```

- Builder (Строитель) - пошаговое создание сложных объектов
```
class Car {
    private String model;
    private int year;
    private boolean isElectric;

    // Приватный конструктор (только через Builder)
    private Car(CarBuilder builder) {
        this.model = builder.model;
        this.year = builder.year;
        this.isElectric = builder.isElectric;
    }

    // Вложенный статический класс Builder
    public static class CarBuilder {
        private String model;
        private int year;
        private boolean isElectric;

        public CarBuilder setModel(String model) {
            this.model = model;
            return this; // возвращаем сам билдер (цепочка вызовов)
        }
        public CarBuilder setYear(int year) {
            this.year = year;
            return this; // возвращаем сам билдер (цепочка вызовов)
        }
        public CarBuilder setElectric(boolean isElectric) {
            this.isElectric = isElectric;
            return this; // возвращаем сам билдер (цепочка вызовов)
        }

        public Car build() {
            return new Car(this); // применение всех накопленных параметров
        }
    }
}
```

- Prototype (Прототип) - новые объекты создаются по образу и подобию уже существующих объектов, т.е. по их прототипу (происходит клонирование переданного объекта)
```
abstract class Prototype implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

- +факт: Minecraft использует паттерн Prototype для создания мобов
