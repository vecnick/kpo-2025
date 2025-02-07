package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Педальный двигатель.
 */
@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    /**
     * Проверяет на совместимость покупателя с двигателем.
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    /**
     * Конструктор.
     *
     * @param size размер педалей
     */
    public PedalEngine(int size) {
        this.size = size;
    }
}
