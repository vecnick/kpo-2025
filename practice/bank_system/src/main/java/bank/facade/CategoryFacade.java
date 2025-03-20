package bank.facade;

import bank.Factories.CategoryFactory;
import bank.Factories.ExporterFactory;
import bank.Factories.ImporterFactory;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.enums.DomainType;
import bank.enums.OperationType;
import bank.enums.ReportFormat;
import bank.exporter.Exporter;
import bank.importer.ImporterContext;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import bank.report.ReportBankAccount;
import bank.report.ReportCategory;
import bank.services.CategoryService;
import bank.storages.CategoryStorage;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryFacade {
    private final ImporterContext<ReportCategory> importerContext = new ImporterContext<>();
    private final ImporterFactory<ReportCategory> importerFactory = new ImporterFactory<>();
    private final ExportVisitor exportVisitor = new ExportVisitor();
    private final ExporterFactory exporterFactory = new ExporterFactory();
    private final CategoryFactory factory = new CategoryFactory();
    private final CategoryStorage storage = new CategoryStorage();
    private final CategoryService service = new CategoryService(storage);

    public void addCategory(OperationType type, String name) {
        service.addCategory(factory, type, name);
    }

    public List<Category> getCategories() {
        return service.getCategories();
    }

    public Optional<Category> getCategoryById(int id) {
        return service.getCategoryById(id);
    }

    public void removeCategoryById(int id) {
        service.removeCategoryById(id);
    }

    static public List<Category> getCategoriesIncome(List<Category> categories) {
        return CategoryService.getCategoriesOutcome(categories);
    }

    static public List<Category> getCategoriesOutcome(List<Category> categories) {
        return CategoryService.getCategoriesIncome(categories);
    }

    public void exportCategories(ReportFormat reportFormat) throws IllegalAccessException, IOException {
        List<Category> categories = getCategories();

        List<ReportCategory> reportCategories = categories.stream()
                .map(ReportCategory::new) // вызываем конструктор ReportCategory
                .collect(Collectors.toList());

        Report<ReportCategory> report = new Report<>(DomainType.CATEGORY, reportCategories);

        Exporter exporter = exporterFactory.create(reportFormat);
        exporter.accept(exportVisitor, report); // определяем тип к которому обращаемся и вызываем его через visitor
    }

    public void importCategories(String filename) throws IOException {
        ImporterStrategy<ReportCategory> strategy = importerFactory.create(filename);
        importerContext.setStrategy(strategy);

        Report<ReportCategory> report = importerContext.parse(ReportCategory.class, filename);

        service.fillCategoriesByReports(report.content());
    }
}
