package HSEBank.Interfaces;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Analytics.BankAccountAnalytics;
import HSEBank.Enums.OperationTypes;

import java.time.LocalDate;
import java.util.Optional;

public interface BankAccountServiceI {
    int createBankAccount(String accountName);

    Optional<BankAccount> getBankAccountDetails(int accountId);

    int deleteBankAccount(int accountId);

    int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName, String description);

    int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName);

    Optional<BankAccountAnalytics> getBankAccountAnalytics(int accountId, LocalDate startDate, LocalDate endDate);

    void exportServiceInfo(String filename);

    void importServiceInfo(String filename);

    double recalculateBalance(int accountID) throws IllegalStateException;
}