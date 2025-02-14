package hse.kpo.interfaces;

import hse.kpo.domains.Catamaran;

/**
 * Интерфейс фабрики катамаранов.
 */
public interface ICatamaranFactory<TParams> {
    Catamaran createCatamaran(TParams catamaranParams, int catamaranNumber);
}
