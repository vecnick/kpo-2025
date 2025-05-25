package hse.kpo.domains;
import java.util.List;

public class ReportJson {
    public String title;
    public Content content;

    public ReportJson(String title, List<String> customersBefore, List<String> sells, List<String> customersAfter) {
        this.title = title;
        this.content = new Content(customersBefore, sells, customersAfter);
    }

    // Вложенный класс Content с 3 разделами
    static class Content {
        public List<String> customers_before;
        public List<String> sells;
        public List<String> customers_after;

        public Content(List<String> customersBefore, List<String> sells, List<String> customersAfter) {
            this.customers_before = customersBefore;
            this.sells = sells;
            this.customers_after = customersAfter;
        }
    }
}
