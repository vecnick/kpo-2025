package bank.domains;

import bank.report.ReportBankAccount;
import lombok.Getter;
import lombok.ToString;

/**
 * Информация о банковском аккаунте пользователя
 */
@ToString
@Getter
public class BankAccount {
    private final int id;
    private final String name;
    private int balance;

    /**
     * Создаяние BankAccount
     *
     * @param id - id счёта
     * @param name - название счёта
     * @param balance - баланс счёта
     */
    public BankAccount(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}
