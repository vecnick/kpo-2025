package studying;

import java.util.Objects;

/**
 * Класс, продающий автомобили
 */
public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    /**
     * Конструктор класса HseCarService
     * @param carProvider - класс, предоставляющий машины
     * @param customersProvider - класс, предоставляющий покупателей
     */
    public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider)
    {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    /**
     * Метод, назначающий покупателям подходящие им машины
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