package finance.interfaces;

public interface IExportable {
    void accept(IExportVisitor visitor, String fileName, boolean append);
}
