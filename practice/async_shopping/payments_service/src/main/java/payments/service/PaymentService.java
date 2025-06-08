package payments.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import payments.entity.BalanceAccount;
import payments.enums.BalanceAccountRequestResult;
import payments.interfaces.IBalanceAccountFactory;
import payments.interfaces.IPaymentService;
import payments.repository.BalanceAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    private final BalanceAccountRepository accountRepository;
    private final IBalanceAccountFactory accountFactory;

    public PaymentService(BalanceAccountRepository accountRepository, IBalanceAccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
    }

    @Override
    public Optional<BalanceAccount> createAccount(int userId) {
        try {
            boolean existed = accountRepository.existsById(userId);
            if (existed) {
                System.out.println("PaymentService: createAccount: счёт для данного пользователя уже создан");
                return Optional.empty();
            }

            BalanceAccount account = accountFactory.create(userId);
            return Optional.of(accountRepository.save(account));
        } catch (Exception e) {
            System.out.println("PaymentService: createAccount: не удалось сохранить счёт в базу данных");
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteAccount(int userId) {
        try {
            boolean existed = accountRepository.existsById(userId);
            accountRepository.deleteById(userId);
            if (!existed) { System.out.println("PaymentService: deleteAccount: счёт с id=" + userId + " не был найден"); }
            return existed;
        } catch (Exception e) {
            System.out.println("PaymentService: deleteAccount: не удалось удалить счёт в базе данных");
            return false;
        }
    }

    @Override
    public Optional<List<BalanceAccount>> getAllAccounts() {
        try {
            return Optional.of(accountRepository.findAll());
        } catch (Exception e) {
            System.out.println("PaymentService: getAllAccounts: не удалось получить список записей базы данных");
            return Optional.empty();
        }
    }

    @Override
    public Optional<BalanceAccount> getAccount(int userId) {
        try {
            return accountRepository.findById(userId);
        } catch (Exception e) {
            System.out.println("PaymentService: getAccount: счёт с id=" + userId + " не был найден");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getBalanceByUserId(int userId) {
        try {
            return accountRepository.getBalanceByUserId(userId);
        } catch (Exception e) {
            System.out.println("PaymentService: getBalanceByUserId: не удалось получить баланс по id пользователя");
            return Optional.empty();
        }
    }

    @Override
    public Pair<BalanceAccount, BalanceAccountRequestResult> addBalanceByUserId(int userId, int value) {
        try {
            boolean notChanged = (0 == accountRepository.addBalanceByUserId(userId, value));
            if (notChanged) {
                System.out.println("PaymentService: addBalanceByUserId: счёт с id=" + userId + " не был найден");
                return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.NOT_FOUND);
            }

            Optional<BalanceAccount> result = getAccount(userId);
            if (result.isEmpty()) { return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.FAIL); }
            return Pair.of(result.get(), BalanceAccountRequestResult.SUCCESS);

        } catch (Exception e) {
            System.out.println("PaymentService: addBalanceByUserId: не удалось увеличить счёт в базе данных");
            return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.FAIL);
        }
    }

    @Override
    public Pair<BalanceAccount, BalanceAccountRequestResult> subBalanceByUserId(int userId, int value) {
        try {
            boolean notChanged = (0 == accountRepository.subBalanceByUserId(userId, value));
            if (notChanged) {
                System.out.println("PaymentService: subBalanceByUserId: счёт с id=" + userId + " не был найден");
                return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.NOT_FOUND);
            }

            Optional<BalanceAccount> result = getAccount(userId);
            if (result.isEmpty()) { return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.FAIL); }
            return Pair.of(result.get(), BalanceAccountRequestResult.SUCCESS);

        } catch (Exception e) {
            System.out.println("PaymentService: subBalanceByUserId: не удалось уменьшить счёт в базе данных");
            return Pair.of(accountFactory.createEmpty(), BalanceAccountRequestResult.FAIL);
        }
    }
}
