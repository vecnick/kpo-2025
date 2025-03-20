package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.Catamaran;

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
     * @param catamaranNumber номер
     * @return {@link Catamaran}
     */
    Catamaran create(T catamaranParams, int catamaranNumber);
}
