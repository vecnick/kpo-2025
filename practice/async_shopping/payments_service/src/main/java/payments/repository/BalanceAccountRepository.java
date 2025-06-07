package payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import payments.entity.BalanceAccount;

import java.util.Optional;

public interface BalanceAccountRepository extends JpaRepository<BalanceAccount, Integer> {

    // Дополнительные запросы

    @Query("""
            SELECT ba.balance FROM BalanceAccount ba
            WHERE ba.userId = :userId
            """)
    Optional<Integer> getBalanceByUserId(int userId);

    @Transactional
    @Modifying
    @Query("""
            UPDATE BalanceAccount ba
            SET ba.balance = ba.balance + :value
            WHERE ba.userId = :userId
            """)
    int addBalanceByUserId(int userId, int value);

    @Transactional
    @Modifying
    @Query("""
            UPDATE BalanceAccount ba
            SET ba.balance = ba.balance - :value
            WHERE ba.userId = :userId
            """)
    int subBalanceByUserId(int userId, int value);
}
