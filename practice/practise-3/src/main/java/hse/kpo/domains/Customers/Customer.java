package hse.kpo.domains.Customers;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Ships.Ship;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Класс покупателя.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name="Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int legPower;

    @Column(nullable = false)
    private int handPower;

    @Column(nullable = false)
    private int iq;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catamaran_id")
    private Ship ship;

    /**
     * Конструктор.
     *
     * @param name      - имя
     * @param legPower  - сила ног
     * @param handPower - сила рук
     * @param iq        - IQ
     */
    @Builder
    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }

    public Customer() {
    }
}
