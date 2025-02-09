package hse.kpo.domains;

import hse.kpo.interfaces.EngineI;
import lombok.ToString;

/**
 * Класс ручного двигателя.
 */
@ToString
public class HandEngineI implements EngineI {
    /**
     * Метод, проверяющий, подходит ли ручной двигатель пользователю.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return - true если двигатель подходит для покупателя
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}

