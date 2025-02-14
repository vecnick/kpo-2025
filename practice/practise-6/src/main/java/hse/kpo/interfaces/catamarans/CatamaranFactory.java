package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.Catamaran;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <ProductionParams> параметры для фабрик
 */
public interface CatamaranFactory<ProductionParams> {
    /**
     * Метод создания катамаранов.
     *
     * @param catamaranParams параметры для создания
     * @param catamaranNumber номер
     * @return {@link Catamaran}
     */
    Catamaran create(ProductionParams catamaranParams, int catamaranNumber);
}
