package hse.kpo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hse.kpo.entities.BankAccount;
import hse.kpo.entities.Category;
import hse.kpo.entities.Operation;
import hse.kpo.storages.IdEntityStorage;

@Configuration
public class IdEntityStorageConfig {

    @Bean
    public IdEntityStorage<BankAccount> BankAccountStorage() {
        return new IdEntityStorage<>();
    }

    @Bean
    public IdEntityStorage<Category> CategoryStorage() {
        return new IdEntityStorage<>();
    }

    @Bean
    public IdEntityStorage<Operation> OperationStorage() {
        return new IdEntityStorage<>();
    }
}
