package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

@ToString
public class FlyingEngine implements IEngine{

    /** 
     * Проверка совместимости летающего двигателя и покупателя.
     * 
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @return true, если двигатель подходит покупателю
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIQ() > 300;
    } 
    
}
