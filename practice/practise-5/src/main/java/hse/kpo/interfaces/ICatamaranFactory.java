package hse.kpo.interfaces;

import hse.kpo.domains.Catamaran;

public interface ICatamaranFactory<TParams> {
    Catamaran create(TParams catamaranParams, int carNumber);
}
