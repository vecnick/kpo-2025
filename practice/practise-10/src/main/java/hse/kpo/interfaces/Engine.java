package hse.kpo.interfaces;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hse.kpo.domains.Customer;
import hse.kpo.domains.HandEngine;
import hse.kpo.domains.LevitationEngine;
import hse.kpo.domains.PedalEngine;
import hse.kpo.enums.ProductionTypes;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HandEngine.class, name = "hand"),
        @JsonSubTypes.Type(value = PedalEngine.class, name = "pedal"),
        @JsonSubTypes.Type(value = LevitationEngine.class, name = "levitation")
})
public interface Engine {

    String toString();

    /**
     * Метод для проверки совместимости двигателя с покупателем.
     *
     * @param customer - покупатель, с которым мы сравниваем двигатель
     * @param type - тип объекта
     * @return true, если двигатель подходит покупателю
     */
    boolean isCompatible(Customer customer, ProductionTypes type);
}
