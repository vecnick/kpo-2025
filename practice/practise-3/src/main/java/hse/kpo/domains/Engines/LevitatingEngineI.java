package hse.kpo.domains.Engines;

import hse.kpo.Enums.Types;
import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс левитирующего двигателя.
 */
@ToString
@Getter
public class LevitatingEngineI extends AbstractEngine {
    /**
     * Метод, проверяющий, подходит ли автомобиль для покупателя.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer, Types type) {
        return customer.getIq() > 300;
    }
}
