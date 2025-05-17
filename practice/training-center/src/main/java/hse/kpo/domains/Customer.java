package hse.kpo.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, описывающий покупателя.
 */
@Getter
@Setter
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int legPower;

    @Column(nullable = false)
    private int handPower;

    @Column(nullable = false)
    private int iq;

    public Customer(int id, String name, int legPower, int handPower, int iq) {
        this.id = id;
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}
