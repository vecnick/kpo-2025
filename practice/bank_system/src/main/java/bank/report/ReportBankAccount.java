package bank.report;

import bank.domains.BankAccount;

import java.util.List;

public class ReportBankAccount {
    public int id;
    public String name;
    public int balance;

    // Пустой конструктор необходим для импорта
    public ReportBankAccount() {
        this(0, null, 0);
    }

    public ReportBankAccount(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public ReportBankAccount(BankAccount bankAccount) {
        this.id = bankAccount.getId();
        this.name = bankAccount.getName();
        this.balance = bankAccount.getBalance();
    }
}
