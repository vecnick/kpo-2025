package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Ручной двигатель
 */
@ToString
public class HandEngine implements IEngine {
    /**
     * Проверяет на совместимость покупателя с двигателем
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
