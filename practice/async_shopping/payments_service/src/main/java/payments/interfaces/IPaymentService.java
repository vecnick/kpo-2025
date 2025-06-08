package payments.interfaces;

import org.apache.commons.lang3.tuple.Pair;
import payments.entity.BalanceAccount;
import payments.enums.BalanceAccountRequestResult;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    Optional<BalanceAccount> createAccount(int userId);
    boolean deleteAccount(int userId);

    Optional<List<BalanceAccount>> getAllAccounts();
    Optional<BalanceAccount> getAccount(int userId);
    Optional<Integer> getBalanceByUserId(int userId);

    Pair<BalanceAccount, BalanceAccountRequestResult> addBalanceByUserId(int userId, int value);
    Pair<BalanceAccount, BalanceAccountRequestResult> subBalanceByUserId(int userId, int value);
}
