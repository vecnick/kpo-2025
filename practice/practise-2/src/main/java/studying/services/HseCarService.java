package studying.services;

import studying.interfaces.ICarProvider;
import studying.interfaces.ICustomerProvider;

import java.util.Objects;

/**
 * Класс, управляющий продажами автомобилей покупателям
 * имеет экземпляр класса, управляющего автомобилями (carProvider), и экземпляр класса, управляющего покупателями (customersProvider)
 */
public class HseCarService {

    private final ICarProvider carProvider;

    private final ICustomerProvider customerProvider;

    /**
     * Конструктор для создания экземпляр класса, управляющего продажами автомобилей
     *
     * @param carProvider - экземпляр класса, управляющего автомобилями
     * @param customersProvider - экземпляр класса, управляющего покупателями
     */
    public HseCarService(ICarProvider carProvider, ICustomerProvider customersProvider)
    {
        this.carProvider = carProvider;
        this.customerProvider = customersProvider;
    }

    /**
     * Назначение подходящей машины для покупателя
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