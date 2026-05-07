package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.OperationLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperationLogJpaRepository extends JpaRepository<OperationLogJpaEntity, Long> {

    List<OperationLogJpaEntity> findByProductId(String productId);

    List<OperationLogJpaEntity> findByProductIdStartingWith(String prefix);
}
