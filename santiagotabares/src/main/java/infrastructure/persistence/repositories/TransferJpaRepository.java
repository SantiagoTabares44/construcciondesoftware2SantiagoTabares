package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.TransferJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransferJpaRepository extends JpaRepository<TransferJpaEntity, String> {

    @Query("SELECT t FROM TransferJpaEntity t WHERE t.origin.accountNumber = :accountNumber OR t.destination.accountNumber = :accountNumber")
    List<TransferJpaEntity> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
