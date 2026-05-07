package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.LoanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanJpaRepository extends JpaRepository<LoanJpaEntity, String> {

    List<LoanJpaEntity> findByClientIdentification(String clientId);
}
