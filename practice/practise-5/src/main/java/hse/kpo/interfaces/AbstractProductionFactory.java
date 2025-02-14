package hse.kpo.interfaces;

public interface AbstractProductionFactory<TParams> {
    ICarFactory<TParams> createCarFactory();
    ICatamaranFactory<TParams> createCatamaranFactory();
}
