package studying.interfaces;

import studying.domains.Car;
import studying.domains.Customer;

public interface ICarProvider {

    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
