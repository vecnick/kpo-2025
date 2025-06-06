package hse.kpo.repositories;

import java.util.List;
import java.util.Locale;
import hse.kpo.domains.cars.Car;
import hse.kpo.domains.catamarans.Catamaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatamaranRepository extends JpaRepository<Catamaran, Integer> {
    @Query("""
        SELECT c 
        FROM Catamaran c 
        JOIN c.engine e 
        WHERE e.type = :engineType 
        AND c.vin > :minVin
    """)
    List<Catamaran> findCatamaransByEngineTypeAndVinGreaterThan(
            @Param("engineType") String engineType,
            @Param("minVin") Integer minVin
    );
}