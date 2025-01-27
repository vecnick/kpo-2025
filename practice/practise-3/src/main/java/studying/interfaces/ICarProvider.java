package studying.interfaces;

import studying.domains.Car;
import studying.domains.Customer;

public interface ICarProvider {

    /**
     * Метод для создания получения подходящего для покупателя автомобиля.
     * 
     * @param customer - покупатель
     * @return - подходящий автомобиль
     */
    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
