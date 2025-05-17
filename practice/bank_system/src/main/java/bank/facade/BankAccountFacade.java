package bank.facade;

import bank.factories.BankAccountFactory;
import bank.factories.ExporterFactory;
import bank.factories.ImporterFactory;
import bank.commands.CommandsExecutor;
import bank.commands.RecalculateBalanceCommand;
import bank.decorators.CommandsExecutorWithTimer;
import bank.domains.BankAccount;
import bank.enums.DomainType;
import bank.importer.ImporterContext;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import bank.enums.ReportFormat;
import bank.exporter.Exporter;
import bank.report.ReportBankAccount;
import bank.services.BankAccountService;
import bank.services.OperationService;
import bank.visitors.ExportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BankAccountFacade {
    private final CommandsExecutor commandsExecutor;
    private final CommandsExecutorWithTimer commandsExecutorWithTimer;
    private final ImporterContext<ReportBankAccount> importerContext;
    private final ImporterFactory<ReportBankAccount> importerFactory;
    private final ExportVisitor exportVisitor;
    private final ExporterFactory exporterFactory;
    private final OperationService operationService;
    private final BankAccountFactory factory;
    private final BankAccountService service;

    public void addAccount(String name, double balance) {
        service.addAccount(factory, name, balance);
    }

    public List<BankAccount> getAccounts() {
        return service.getAccounts();
    }

    public Optional<BankAccount> getAccountById(int id) {
        return service.getAccountById(id);
    }

    public void removeAccountById(int id) {
        service.removeAccountById(id);
    }

    public void exportAccounts(ReportFormat reportFormat) throws IllegalAccessException, IOException {
        List<BankAccount> accounts = getAccounts();

        List<ReportBankAccount> reportBankAccounts = accounts.stream()
                .map(ReportBankAccount::new) // вызываем конструктор ReportBankAccount
                .collect(Collectors.toList());

        Report<ReportBankAccount> report = new Report<>(DomainType.BANKACCOUNT, reportBankAccounts);

        Exporter exporter = exporterFactory.create(reportFormat);
        exporter.accept(exportVisitor, report); // определяем тип к которому обращаемся и вызываем его через visitor
    }

    public void importAccounts(String filename) throws IOException {
        ImporterStrategy<ReportBankAccount> strategy = importerFactory.create(filename);
        importerContext.setStrategy(strategy);

        Report<ReportBankAccount> report = importerContext.parse(ReportBankAccount.class, filename);

        service.fillAccountsByReports(report.content());
    }

    public void recalculateBalance(int bankAccountId, String dateFrom, String dateTo) {
        RecalculateBalanceCommand command = new RecalculateBalanceCommand(service, operationService, bankAccountId, dateFrom, dateTo);
        commandsExecutor.run(command);
    }

    public void recalculateBalanceWithTimer(int bankAccountId, String dateFrom, String dateTo) {
        RecalculateBalanceCommand command = new RecalculateBalanceCommand(service, operationService, bankAccountId, dateFrom, dateTo);
        commandsExecutorWithTimer.run(command);
    }


}
