package hse.kpo.domains;

import hse.kpo.domains.cars.Car;
import hse.kpo.domains.catamarans.Catamaran;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Класс, описывающий покупателя.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int legPower;

    @Column(nullable = false)
    private int handPower;

    @Column(nullable = false)
    private int iq;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "catamaran_id")
    private Catamaran catamaran;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;

    public Car getCar() {
        return (cars != null && !cars.isEmpty()) ? cars.get(0) : null;
    }

    public Customer(String name, int legPower, int handPower, int iq) {
        this.name = name;
        this.legPower = legPower;
        this.handPower = handPower;
        this.iq = iq;
    }
}
