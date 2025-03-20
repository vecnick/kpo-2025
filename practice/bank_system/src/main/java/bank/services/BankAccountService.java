package bank.services;

import bank.domains.BankAccount;
import bank.interfaces.IBankAccountFactory;
import bank.interfaces.IBankAccountStorage;
import bank.report.ReportBankAccount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankAccountService {
    private final IBankAccountStorage bankAccountsStorage;

    public BankAccountService(IBankAccountStorage bankAccountsStorage) {
        this.bankAccountsStorage = bankAccountsStorage;
    }

    public void addAccount(IBankAccountFactory bankAccountFactory, String name, int balance) {
        bankAccountsStorage.addAccount(bankAccountFactory, name, balance);
    }

    public void fillAccountsByReports(List<ReportBankAccount> reports) {
        List<BankAccount> accounts = reports.stream()
            .map(report -> new BankAccount(
                    report.id,
                    report.name,
                    report.balance
            )).collect(Collectors.toList());

        bankAccountsStorage.setAccounts(accounts);
    }

    public List<BankAccount> getAccounts() {
        return bankAccountsStorage.getAccounts();
    }

    public Optional<BankAccount> getAccountById(int id) {
        return getAccounts().stream()
                .filter(ac -> ac.getId() == id) // фильтруем
                .findFirst(); // берём первый попавшийся
    }

    public void removeAccountById(int id) {
        bankAccountsStorage.removeAccountById(id);
    }
}
