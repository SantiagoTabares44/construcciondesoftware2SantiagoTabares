package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.BankProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankProductJpaRepository extends JpaRepository<BankProductJpaEntity, String> {
}
