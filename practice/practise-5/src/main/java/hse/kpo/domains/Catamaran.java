package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.Getter;
import lombok.ToString;

/**
 * Катамаран.
 */
@ToString
public class Catamaran {
    private IEngine engine;

    @Getter
    private int vin;

    /**
     * Конструктор.
     *
     * @param vin номер катамарана
     * @param engine объект двигателя
     */
    public Catamaran(int vin, IEngine engine) {
        this.vin = vin;
        this.engine = engine;
    }

    /**
     * Проверяет на совместимость покупателя с катамараном.
     *
     * @param customer покупатель
     * @return совместимость с катамараном в виде булевого значения
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer); // внутри метода просто вызываем соответствующий метод двигателя
    }
}
