package hse.kpo.domains;

import hse.kpo.Enums.Types;
import hse.kpo.domains.Customers.Customer;
import hse.kpo.interfaces.EngineI;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "engine_type")
public class AbstractEngine implements EngineI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "engine_type", insertable = false, updatable = false)
    private String type; // Автоматически заполняется дискриминатором


    @Override
    public boolean isCompatible(Customer customer, Types type) {
        return false;
    }
}