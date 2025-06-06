package hse.kpo.repositories;

import hse.kpo.domains.Customer;
import hse.kpo.domains.catamarans.Catamaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("""
        SELECT c 
        FROM Customer c 
        WHERE c.legPower >= :minLegPower\s
        AND c.handPower >= :minHandPower
    """)
    List<Catamaran> findCatamaransByEngineTypeAndVinGreaterThan(
            @Param("engineType") String engineType,
            @Param("minVin") Integer minVin
    );

    void deleteByName(String name);
}