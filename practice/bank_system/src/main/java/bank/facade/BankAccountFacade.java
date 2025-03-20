package bank.facade;

import bank.Factories.BankAccountFactory;
import bank.Factories.ExporterFactory;
import bank.domains.BankAccount;
import bank.enums.DomainType;
import bank.export.ExporterCSV;
import bank.report.Report;
import bank.enums.ReportFormat;
import bank.export.Exporter;
import bank.report.ReportBankAccount;
import bank.report.ReportOperation;
import bank.services.BankAccountService;
import bank.storages.BankAccountStorage;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankAccountFacade {
    private final ExportVisitor exportVisitor = new ExportVisitor();
    private final ExporterFactory exporterFactory = new ExporterFactory();
    private final BankAccountFactory factory = new BankAccountFactory();
    private final BankAccountStorage storage = new BankAccountStorage();
    private final BankAccountService service = new BankAccountService(storage);

    public void addAccount(String name, int balance) {
        service.addAccount(factory, name, balance);
    }

    public List<BankAccount> getAccounts() {
        return service.getAccounts();
    }

    public Optional<BankAccount> getOperationById(int id) {
        return service.getOperationById(id);
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
}
