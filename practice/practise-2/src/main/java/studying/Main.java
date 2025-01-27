package studying;

/**
 * Точка входа в программу
 */
public class Main {
    /**
     * Запуск программы
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("HSE");

        var cs = new CarService();
        var custStorage = new CustomerStorage();
        var hseCarService = new HseCarService(cs, custStorage);
        var pedalCarFact = new PedalCarFactory();
        var handCarFact = new HandCarFactory();
        var levitatingCarFact = new LevitatingCarFactory();

        custStorage.addCustomer(new Customer("Alisa", 6, 4, 98));
        custStorage.addCustomer(new Customer("Bob", 4, 6, 102));
        custStorage.addCustomer(new Customer("Chris", 6, 6, 200));
        custStorage.addCustomer(new Customer("Daemon", 4, 4, 340));
        custStorage.addCustomer(new Customer("Eva", 1, 2, 500));

        cs.addCar(pedalCarFact, new PedalEngineParams(10));
        cs.addCar(pedalCarFact, new PedalEngineParams(100));
        cs.addCar(handCarFact, new EmptyEngineParams());
        cs.addCar(handCarFact, new EmptyEngineParams());
        cs.addCar(levitatingCarFact, new EmptyEngineParams());
        cs.addCar(levitatingCarFact, new EmptyEngineParams());

        System.out.println("Customers: ");
        for (Customer customer : custStorage.getCustomers()) {
            System.out.print(customer + " ");
        }
        System.out.println();

        hseCarService.sellCars();

        System.out.println("Customers: ");
        for (Customer customer : custStorage.getCustomers()) {
            System.out.print(customer + " ");
        }
        System.out.println();
    }
}
