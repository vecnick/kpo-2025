package studying;

public class Main {
    public static void main(String[] args) {
        var factory = new FactoryAF();

        // Первый день продаж
        System.out.println("====");
        System.out.println("День 1");

        // Добавим автомобили
        factory.addCar(1);
        factory.addCar(2);
        factory.addCar(3);
        factory.addCar(4);

        // Добавим покупателей
        factory.addCustomer(new Customer("Вася"));
        factory.addCustomer(new Customer("Вова"));
        factory.addCustomer(new Customer("Света"));

        // Выводим информацию
        System.out.println();
        System.out.println("== Автомобили до продажи ==");
        factory.printCars();
        System.out.println();
        System.out.println("== Покупатели до продажи ==");
        factory.printCustomers();

        // Продаем автомобили
        factory.saleCar();

        // Выводим информацию
        System.out.println();
        System.out.println("== Автомобили после продажи ==");
        factory.printCars();
        System.out.println();
        System.out.println("== Покупатели после продажи ==");
        factory.printCustomers();

        /* ============================================= */
        // Второй день продаж
        System.out.println("====");
        System.out.println("День 2");
        // Добавим автомобили
        factory.addCar(2);
        factory.addCar(3);
        // Добавим покупателей
        factory.addCustomer(new Customer("Сережа"));
        factory.addCustomer(new Customer("Саша"));
        factory.addCustomer(new Customer("Миша"));

        // Выводим информацию
        System.out.println();
        System.out.println("== Автомобили до продаж ==");
        factory.printCars();
        System.out.println();
        System.out.println("== Покупатели до продажи ==");
        factory.printCustomers();

        // Продаем автомобили
        factory.saleCar();

        // Выводим информацию
        System.out.println();
        System.out.println("== Автомобили после продаж ==");
        factory.printCars();
        System.out.println();
        System.out.println("== Покупатели после продажи ==");
        factory.printCustomers();
    }
}