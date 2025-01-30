package studying.services;

import studying.interfaces.ICarProvider;
import studying.interfaces.ICustomerProvider;

import java.util.Objects;

/**
 * Продаёт машины из carProvider покупателям из customerProvider
 */
public class HseCarService {
    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    /**
     * Конструктор
     *
     * @param carProvider "хранилище" машин
     * @param customersProvider "хранилище" покупателей
     */
    public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider)
    {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    /**
     * Продаёт машины
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