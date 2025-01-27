package studying;

import java.util.Objects;

/**
 * A service that provides cars to customers.
 */
public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    /**
     * Creates a new instance of the service.
     * @param carProvider the provider of cars
     * @param customerProvider the provider of customers
     */
    public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider)
    {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    /**
     * Sells cars to customers.
     * This method takes all customers that do not have a car and assigns them a car
     * from the car provider that is compatible with the customer.
     */
    public void sellCars()
    {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = carProvider.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                    }
                });
    }
}
