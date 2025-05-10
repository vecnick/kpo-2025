package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Класс левитирующего двигателя
 */
@ToString
public class LevitatingEngine implements IEngine {
    /**
     * Определение совместимости покупателя с левитирующим двигателемы
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если интеллект покуптеля больше 300, иначе false
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    }
}
