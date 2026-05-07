package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.BankAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankAccountJpaRepository extends JpaRepository<BankAccountJpaEntity, String> {

    boolean existsByAccountNumber(String accountNumber);

    List<BankAccountJpaEntity> findByOwnerIdentification(String clientId);
}
