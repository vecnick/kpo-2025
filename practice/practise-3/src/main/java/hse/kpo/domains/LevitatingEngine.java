package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.ToString;

/**
 * Левитирующий двигатель.
 */
@ToString
public class LevitatingEngine implements IEngine {
    /**
     * Проверяет на совместимость покупателя с двигателем.
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}
