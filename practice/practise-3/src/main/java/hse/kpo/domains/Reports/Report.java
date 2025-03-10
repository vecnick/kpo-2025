package hse.kpo.domains.Reports;

public record Report(String title, String content) {
    @Override
    public String toString() {
        return String.format("%s\n\n%s", title, content);
    }
}
