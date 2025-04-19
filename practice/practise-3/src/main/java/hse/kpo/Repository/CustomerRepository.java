package hse.kpo.Repository;

import hse.kpo.domains.Cars.Car;
import hse.kpo.domains.Customers.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Transactional
    @Query("""
        delete from Customer c  where c.name = :name
    """)
    void deleteByName(
            @Param("name") String name
    );
}
