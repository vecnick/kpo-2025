package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.Catamaran;
import hse.kpo.domains.Customer;

public interface CatamaranProvider {

    /**
     * Метод покупки катамарана.
     *
     * @param customer - покупатель
     * @return - {@link Catamaran}
     */
    Catamaran takeCatamaran(Customer customer);
}
