package bank.commands;

import bank.domains.BankAccount;
import bank.interfaces.ICommand;
import bank.services.BankAccountService;
import bank.services.OperationService;

public class RecalculateBalanceCommand implements ICommand {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    int bankAccountId;
    String dateFrom;
    String dateTo;

    public RecalculateBalanceCommand(BankAccountService bankAccountService, OperationService operationService, int bankAccountId, String dateFrom, String dateTo) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
        this.bankAccountId = bankAccountId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public void execute() {
        double delta = operationService.getAmountDifferenceByPeriod(dateFrom, dateTo);
        BankAccount account = bankAccountService.getAccountById(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Аккаунт с данным id не найден"));
        account.setBalance(account.getBalance() + delta);
    }
}
