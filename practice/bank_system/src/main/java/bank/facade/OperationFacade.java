package bank.facade;

import bank.Factories.ImporterFactory;
import bank.Factories.OperationFactory;
import bank.Factories.ExporterFactory;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.domains.Operation;
import bank.enums.DomainType;
import bank.enums.OperationType;
import bank.enums.ReportFormat;
import bank.exporter.Exporter;
import bank.importer.ImporterContext;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import bank.report.ReportBankAccount;
import bank.report.ReportOperation;
import bank.services.BankAccountService;
import bank.services.CategoryService;
import bank.services.OperationService;
import bank.storages.BankAccountStorage;
import bank.storages.CategoryStorage;
import bank.storages.OperationStorage;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationFacade {
    private final ImporterContext<ReportOperation> importerContext = new ImporterContext<>();
    private final ImporterFactory<ReportOperation> importerFactory = new ImporterFactory<>();
    private final ExportVisitor exportVisitor = new ExportVisitor();
    private final ExporterFactory exporterFactory = new ExporterFactory();
    private final BankAccountStorage bankAccountStorage = new BankAccountStorage();
    private final BankAccountService bankAccountService = new BankAccountService(bankAccountStorage);
    private final CategoryStorage categoryStorage = new CategoryStorage();
    private final CategoryService categoryService = new CategoryService(categoryStorage);
    private final OperationFactory factory = new OperationFactory();
    private final OperationStorage storage = new OperationStorage();
    private final OperationService service = new OperationService(storage);

    public void addOperation(OperationType type, BankAccount bankAccountId, int amount, String description, Category categoryId) {
        service.addOperation(factory, type, bankAccountId, amount, description, categoryId);
    }

    public List<Operation> getOperations() {
        return service.getOperations();
    }

    public Optional<Operation> getOperationById(int id) {
        return service.getOperationById(id);
    }

    public void removeOperationById(int id) {
        service.removeOperationById(id);
    }

    public Map<String, List<Operation>> groupOperationsByCategoryName() {
        return service.groupOperationsByCategoryName();
    }

    public double getAmountDifferenceByPeriod(String dateFrom, String dateTo) {
        return service.getAmountDifferenceByPeriod(dateFrom, dateTo);
    }

    static public List<Operation> getOperationsIncome(List<Operation> operations) {
        return OperationService.getOperationsIncome(operations);
    }

    static public List<Operation> getOperationsOutcome(List<Operation> operations) {
        return OperationService.getOperationsOutcome(operations);
    }

    static public List<Operation> getOperationsByAccount(List<Operation> operations, BankAccount account) {
        return OperationService.getOperationsByAccount(operations, account);
    }

    public void exportOperations(ReportFormat reportFormat) throws IllegalAccessException, IOException {
        List<Operation> operations = getOperations();

        List<ReportOperation> reportOperations = operations.stream()
                .map(ReportOperation::new) // вызываем конструктор ReportOperation
                .collect(Collectors.toList());

        Report<ReportOperation> report = new Report<>(DomainType.OPERATION, reportOperations);

        Exporter exporter = exporterFactory.create(reportFormat);
        exporter.accept(exportVisitor, report); // определяем тип к которому обращаемся и вызываем его через visitor
    }

    public void importOperations(String filename) throws IOException {
        ImporterStrategy<ReportOperation> strategy = importerFactory.create(filename);
        importerContext.setStrategy(strategy);

        Report<ReportOperation> report = importerContext.parse(ReportOperation.class, filename);

        service.fillOperationsByReports(report.content(), bankAccountService, categoryService);
    }

}
