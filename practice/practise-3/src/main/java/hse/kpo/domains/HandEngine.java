package hse.kpo.domains;

import hse.kpo.interfaces.IEngine;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Класс двигателя с ручным приводом
 */
@ToString
@Component // Чтобы сделать сервис видимым контексту spring, нужно добавить аннотацию Component
public class HandEngine implements IEngine {
    /**
     * Определение совместимости покупателя с двигателем с ручным приводом
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если сила рук покупателя больше 5, иначе false
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getHandPower() > 5;
    }
}
