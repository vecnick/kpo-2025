package hse.kpo.interfaces.cars;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.Customer;

public interface CarProvider {

    /**
     * Метод покупки машины.
     *
     * @param customer - покупатель
     * @return - {@link Car}
     */
    Car takeCar(Customer customer);
}
