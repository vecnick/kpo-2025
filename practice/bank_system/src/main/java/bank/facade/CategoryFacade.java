package bank.facade;

import bank.Factories.CategoryFactory;
import bank.Factories.ExporterFactory;
import bank.domains.BankAccount;
import bank.domains.Category;
import bank.enums.DomainType;
import bank.enums.OperationType;
import bank.enums.ReportFormat;
import bank.export.Exporter;
import bank.interfaces.ICategoryFactory;
import bank.interfaces.ICategoryStorage;
import bank.report.Report;
import bank.report.ReportBankAccount;
import bank.report.ReportCategory;
import bank.report.ReportOperation;
import bank.services.CategoryService;
import bank.storages.CategoryStorage;
import bank.visitors.ExportVisitor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryFacade {
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

    public Optional<Category> getOperationById(int id) {
        return service.getOperationById(id);
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
}
