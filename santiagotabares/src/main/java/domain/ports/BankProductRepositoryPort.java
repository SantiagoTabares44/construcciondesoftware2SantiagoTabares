package domain.ports;

import domain.models.BankProduct;
import java.util.Optional;

public interface BankProductRepositoryPort {
    Optional<BankProduct> findByCode(String code);
}
