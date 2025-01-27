package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.interfaces.IEngine;

@ToString
@Getter
public class PedalEngine implements IEngine {
    private final int size;

    /** 
     * Проверка совместимости педального двигателя и покупателя.
     * 
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    public PedalEngine(int size) {
        this.size = size;
    }
}
