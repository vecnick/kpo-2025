package bank.report;

import bank.domains.BankAccount;

public class ReportBankAccount {
    public int id;
    public String name;
    public int balance;

    public ReportBankAccount(BankAccount bankAccount) {
        this.id = bankAccount.getId();
        this.name = bankAccount.getName();
        this.balance = bankAccount.getBalance();
    }
}
