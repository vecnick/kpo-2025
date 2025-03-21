package bank.facade;

import bank.factories.CategoryFactory;
import bank.factories.ExporterFactory;
import bank.factories.ImporterFactory;
import bank.domains.Category;
import bank.enums.DomainType;
import bank.enums.OperationType;
import bank.enums.ReportFormat;
import bank.exporter.Exporter;
import bank.importer.ImporterContext;
import bank.interfaces.ImporterStrategy;
import bank.report.Report;
import bank.report.ReportCategory;
import bank.services.CategoryService;
import bank.visitors.ExportVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryFacade {
    private final ImporterContext<ReportCategory> importerContext;
    private final ImporterFactory<ReportCategory> importerFactory;
    private final ExportVisitor exportVisitor;
    private final ExporterFactory exporterFactory;
    private final CategoryFactory factory;
    private final CategoryService service;

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
