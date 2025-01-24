# Определения

- Наследованние - механизм класса-наследника, который позволяет получить и в дальнейшем использовать методы и поля класса-родителя.

```
class Phone { // Убрали private
    public void call() {}
}

public class MobilePhone extends Phone {
    public void charge() {}
}бласти видимости данных, сокрытие реализации

```
public class File {
    private String filename;
    
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
```

- Полиморфизм - возможность использования одинаковых методов для разных объектов с различными реализациями

```
abstract class Animal {
    public abstract void makeSound();
}
public class Cat extends Animal {
    @Override
    public void makeSound();
}
public class Dog extends Animal {
    @Override
    public void makeSound();
}
public class Main {
    public static void main(String[] args) {
        Animal dog = new Dog();
        Animal cat = new Cat();
        dog.makeSound();
        cat.makeSound();
    }
}
```

- Реализация - использование интерфейсов в классе объекта

```
interface Animal {
    void eat();
}
public class Dog implements Animal {
    @Override
    public void eat();
}
```

- ассоциация - отношение между классами (делится на композицию и агригацию)

- композиция - сильная связь между классами, при которой один класс не может существовать без другого, создаётся и удаляется из памяти вместе с ним

```
class Engine {
    void start();
}

class Car {
    private Engine engine; // двигатель создаётся как часть машины
    
    /*  не обязательно создавать объект другого класса внутри, можно и передать через конструктор.
        главное отличие - классу Engine нет смысла существовать без класса Car */
        
    public C класса Car    this.engine = new Engine(); // двигатель создаётся внутри машины
    }
}
```

- агрегация - слабая связь между классами, при которой один класс используется другим, но при этом способен существовать независимо.

```
class Department {
    String name;

    public Department(String name) {
        this.name = name;
    }
}

class University {
    private String name;
    private Department department; // Агрегация: объект "часть" передаётся
    
    /*  не обязательно передавать объект другого класса через конструктор, можно и создавать его внутри.
        главное отличие - класс Department может существовать независимо */
        
    public University(String name, Department department) {
        this.name = name;
        this.department = department;
    }
}
```
