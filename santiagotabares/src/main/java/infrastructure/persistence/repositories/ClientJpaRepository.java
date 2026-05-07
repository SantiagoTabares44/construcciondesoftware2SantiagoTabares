package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.ClientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJpaRepository extends JpaRepository<ClientJpaEntity, String> {
}
