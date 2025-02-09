package hse.kpo.interfaces;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;

/**
 * Интерфейс класса, возвращающего список доступных для покупки машин.
 */
public interface CarProviderI {
    /**
     * Метод, ищущий подходящую машину для покупателя, и возвращающий ее, если она найдена.
     *
     * @param customer - покупатель, для которого ищем машину
     * @return - null, если подходящей машины нет; Car - если подходящая машина есть
     */
    Car takeCar(Customer customer); // Метод возвращает optional на Car, что означает, что метод может ничего не вернуть
}
