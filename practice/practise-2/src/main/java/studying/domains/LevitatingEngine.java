package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Левитирующий двигатель
 */
@ToString
public class LevitatingEngine implements IEngine {
    /**
     * Проверяет на совместимость покупателя с двигателем
     *
     * @param customer покупатель
     * @return совместимость с машиной в виде булевого значения
     */
    @Override
    public boolean isCompatible(Customer customer) { return customer.getIQ() > 300; }
}
