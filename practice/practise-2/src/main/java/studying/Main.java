package studying;

public class Main {
    public static void main(String[] args) {
        // # Тестирование
        // 1. Создать экземпляр класса `CarService`
        CarService carService = new CarService();
        
        // 2. Создать экземпляр класса `CustomerStorage`
        CustomerStorage customerStorage = new CustomerStorage();
        
        // 3. Создать экземпляр класса `HseCarService`
        HseCarService hseCarService = new HseCarService(carService, customerStorage);
        
        // 4. Создать экземпляр класса `PedalCarFactory`
        PedalCarFactory pedalCarFactory = new PedalCarFactory();
        
        // 5. Создать экземпляр класса `HandCarFactory`
        HandCarFactory handCarFactory = new HandCarFactory();

        LevitationCarFactory levitationCarFactory = new LevitationCarFactory();
        
        // 6. Добавить следующих покупателей:
        customerStorage.addCustomer(new Customer("Customer 1", 6, 4, 50));
        customerStorage.addCustomer(new Customer("Customer 2", 4, 6, 200));
        customerStorage.addCustomer(new Customer("Customer 3", 0, 0, 300));
        customerStorage.addCustomer(new Customer("Customer 4", 4, 4, 2));
        
        // 7. Добавить автомобили:
        carService.addCar(pedalCarFactory, new PedalEngineParams(1));
        carService.addCar(pedalCarFactory, new PedalEngineParams(2));
        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(handCarFactory, new EmptyEngineParams());
        carService.addCar(levitationCarFactory, new EmptyEngineParams());
        
        // 8. Вывести на экран информацию о покупателях и их автомобилях
        customerStorage.getCustomers().forEach(customer -> System.out.println(customer));
        customerStorage.getCustomers().forEach(car -> System.out.println(car));
        
        // 9. Вызвать метод `SellCars`
        hseCarService.sellCars();
        
        // 10. Вывести на экран информацию о покупателях и их автомобилях. Проверить, что результат соответствует следующему:
        //     - Одному покупателю вручен педальный автомобиль
        //     - Одному покупателю вручен автомобиль с ручным приводом
        //     - Одному покупателю вручен любой автомобиль
        //     - Один покупатель остался без автомобиля
        //     - При этом у всех врученных автомобилей различные номера
        customerStorage.getCustomers().forEach(customer -> System.out.println(customer));
    }
}
