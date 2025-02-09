package hse.kpo.domains;

import hse.kpo.interfaces.EngineI;
import lombok.ToString;

/**
 * Класс левитирующего двигателя.
 */
@ToString
public class LevitatingEngineI implements EngineI {
    /**
     * Метод, проверяющий, подходит ли автомобиль для покупателя.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}
