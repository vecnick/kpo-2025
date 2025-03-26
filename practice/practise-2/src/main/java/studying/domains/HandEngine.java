package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Класс двигателя с ручным приводом
 */
@ToString
public class HandEngine implements IEngine {
    /**
     * Определение совместимости покупателя с двигателем с ручным приводом
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если сила рук покупателя больше 5, иначе false
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
