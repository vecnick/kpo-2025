package hse.kpo.interfaces;

import hse.kpo.domains.Car;

/**
 * generic-интерфейс катамаранной фабрики
 *
 * @param <TParams> - параметры двигателя
 */
public interface ICatamaranFactory<TParams> {
    /**
     * Создаёт катамаран с определённым типом двигателя
     *
     * @param catamaranParams - параметры автомобиля
     * @param catamaranNumber - порядковый номер автомобиля на производстве
     * @return бъект автомобиля с двигателем определённого типа
     */
    Car createCatamaran(TParams catamaranParams, int catamaranNumber);
}