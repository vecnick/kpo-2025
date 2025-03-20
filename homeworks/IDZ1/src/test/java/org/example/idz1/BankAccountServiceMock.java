package org.example.idz1;

import HSEBank.Domains.Accounts.BankAccount;
import HSEBank.Domains.Analytics.BankAccountAnalytics;
import HSEBank.Enums.OperationTypes;
import HSEBank.Interfaces.BankAccountServiceI;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class BankAccountServiceMock implements BankAccountServiceI {
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public int createBankAccount(String accountName) {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Optional<BankAccount> getBankAccountDetails(int accountId) {
        return Optional.of(new BankAccount(accountId, "MockAccount", 1000.0));
    }

    @Override
    public int deleteBankAccount(int accountId) {
        return accountId;
    }

    @Override
    public int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName, String description) {
        return idGenerator.getAndIncrement();
    }

    @Override
    public int performOperation(OperationTypes type, int bankAccountId, double amount, LocalDate date, String categoryName) {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Optional<BankAccountAnalytics> getBankAccountAnalytics(int accountId, LocalDate startDate, LocalDate endDate) {
        return Optional.of(new BankAccountAnalytics(accountId, startDate, endDate));
    }

    @Override
    public void exportServiceInfo(String filename) {
    }

    @Override
    public void importServiceInfo(String filename) {
    }

    @Override
    public double recalculateBalance(int accountID) throws IllegalStateException {
        return 5000.0;
    }
}