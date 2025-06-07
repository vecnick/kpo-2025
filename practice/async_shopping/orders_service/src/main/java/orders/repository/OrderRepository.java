package orders.repository;

import orders.entity.Order;
import orders.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
            SELECT o FROM Order o
            WHERE o.userId = :userId
            """)
    List<Order> getOrdersByUserId(int userId);

    @Query("""
            SELECT o.status FROM Order o
            WHERE o.id = :id
            """)
    Optional<OrderStatus> getStatusById(int id);

    @Query("""
            SELECT o.amount FROM Order o
            WHERE o.id = :id
            """)
    Optional<Integer> getAmountById(int id);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Order o
            SET o.status = :status
            WHERE o.id = :id
            """)
    int setStatusById(int id, OrderStatus status);
}

