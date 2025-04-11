package hse.kpo.interfaces.catamarans;

import hse.kpo.domains.catamarans.Catamaran;
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
