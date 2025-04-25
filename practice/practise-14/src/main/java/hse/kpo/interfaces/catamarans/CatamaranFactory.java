package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.catamarans.Catamaran;

/**
 * Интерфейс для определения методов фабрик.
 *
 * @param <T> параметры для фабрик
 */
public interface CatamaranFactory<T> {
    /**
     * Метод создания катамаранов.
     *
     * @param catamaranParams параметры для создания
     * @return {@link Catamaran}
     */
    Catamaran create(T catamaranParams);
}
