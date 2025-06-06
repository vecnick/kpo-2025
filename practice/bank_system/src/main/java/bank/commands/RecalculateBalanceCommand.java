package bank.commands;

import bank.domains.BankAccount;
import bank.domains.Operation;
import bank.interfaces.ICommand;
import bank.services.BankAccountService;
import bank.services.OperationService;

import java.util.List;

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
        BankAccount account = bankAccountService.getAccountById(bankAccountId).orElseThrow(() -> new IllegalArgumentException("Аккаунт с данным id не найден"));
        List<Operation> operations = operationService.getOperations();
        List<Operation> accountOperations = OperationService.getOperationsByAccount(operations, account);
        double delta = operationService.getAmountDifferenceByPeriod(accountOperations, dateFrom, dateTo);

        account.setBalance(account.getBalance() + delta);
    }
}
