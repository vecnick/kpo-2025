package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс двигателя с педалями для ног
 * имеет размер педалей (size)
 */
@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    /**
     * Определение совместимости покупателя с двигателем с педалями
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если сила ног покуптеля больше 5, иначе false
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    /**
     * Конструктор для создания объекта двигателя с педалями
     *
     * @param size - размер педалей
     */
    public PedalEngine(int size) {
        this.size = size;
    }
}