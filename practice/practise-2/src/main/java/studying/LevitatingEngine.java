package studying;
import lombok.ToString;

/**
 * Класс левитирующего двигателя
 */
@ToString
public class LevitatingEngine implements IEngine {
    /**
     * Метод, проверяющий, подходит ли автомобиль для покупателя
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    }
}
