package domain.ports;

import domain.models.BankAccount;
import java.util.Optional;
import java.util.List;

public interface BankAccountRepositoryPort {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
    void save(BankAccount account);
    List<BankAccount> findByClientId(String clientId);
}
