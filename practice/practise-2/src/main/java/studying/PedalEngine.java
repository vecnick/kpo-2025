package studying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Класс педального двигателя
 */
@ToString
@Getter
public class PedalEngine implements IEngine{
    private final int size;

    /**
     * Метод, проверяющий, подходит ли педальный двигатель пользователю
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если педальный двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegPower() > 5;
    }

    /**
     * Конструктор педального двигателя
     * @param size - размер двигателя
     */
    public PedalEngine(int size) {
        this.size = size;
    }
}
