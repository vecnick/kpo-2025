package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.ToString;
import org.springframework.stereotype.Component;

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
