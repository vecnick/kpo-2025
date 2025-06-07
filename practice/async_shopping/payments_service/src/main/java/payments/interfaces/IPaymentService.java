package payments.interfaces;

import payments.entity.BalanceAccount;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    Optional<BalanceAccount> createAccount(int userId);
    boolean deleteAccount(int userId);

    Optional<List<BalanceAccount>> getAllAccounts();
    Optional<BalanceAccount> getAccount(int userId);
    Optional<Integer> getBalanceByUserId(int userId);

    Optional<BalanceAccount> addBalanceByUserId(int userId, int value);
    Optional<BalanceAccount> subBalanceByUserId(int userId, int value);
}
