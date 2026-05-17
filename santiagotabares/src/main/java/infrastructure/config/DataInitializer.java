package infrastructure.config;

import infrastructure.persistence.entities.BankProductJpaEntity;
import infrastructure.persistence.repositories.BankProductJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedBankProducts(BankProductJpaRepository bankProductRepository) {
        return args -> {
            if (bankProductRepository.count() > 0) {
                return;
            }
            bankProductRepository.save(product("AHORROS", "Cuenta de ahorros", "Cuenta de ahorros estándar"));
            bankProductRepository.save(product("CORRIENTE", "Cuenta corriente", "Cuenta corriente para operaciones diarias"));
            bankProductRepository.save(product("NOMINA", "Cuenta nómina", "Cuenta para recepción de nómina"));
        };
    }

    private static BankProductJpaEntity product(String code, String name, String description) {
        BankProductJpaEntity entity = new BankProductJpaEntity();
        entity.setCode(code);
        entity.setName(name);
        entity.setDescription(description);
        return entity;
    }
}
