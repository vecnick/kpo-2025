package hse.kpo.domains.Engines;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс ручного двигателя.
 */
@ToString
@Getter
public class HandEngineI implements EngineI {
    /**
     * Метод, проверяющий, подходит ли ручной двигатель пользователю.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return - true если двигатель подходит для покупателя
     */
    @Override
    public boolean isCompatible(Customer customer, Types type) {
        switch (type) {
            case CAR: return customer.getHandPower() > 5;
            case SHIP: return customer.getHandPower() > 2 && customer.getIq() >= 200;
            case null, default: throw new RuntimeException("This type of production doesn't exist");
        }

    }
}

