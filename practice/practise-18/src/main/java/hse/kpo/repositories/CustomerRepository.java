package hse.kpo.repositories;

import java.util.Optional;
import hse.kpo.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    void deleteByName(String name);
    Optional<Customer> findByName(String name);
}
