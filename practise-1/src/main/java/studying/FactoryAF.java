package studying;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@RequiredArgsConstructor
public class FactoryAF {
    private int carNumber;

    private List<Car> cars = new ArrayList<>();

    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void addCar(int engineSize) {
        var number = ++carNumber;

        cars.add(new Car(number, engineSize));
    }

    public void saleCar() {
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    if (!cars.isEmpty()) {
                        customer.setCar(cars.getFirst());
                        cars.removeFirst();
                    }
                });

        cars.clear();
    }

    public void printCars() {
        cars.stream().map(Car::toString).forEach(System.out::println);
    }

    public void printCustomers() {
        customers.stream().map(Customer::toString).forEach(System.out::println);
    }
}
