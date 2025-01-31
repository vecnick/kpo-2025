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

- SOLID -

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

- Stream-api

- +1 уникальный факт связанный с темами выше или семинаром


