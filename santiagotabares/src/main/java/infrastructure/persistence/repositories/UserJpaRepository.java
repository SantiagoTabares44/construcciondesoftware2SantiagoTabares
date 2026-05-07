package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    List<UserJpaEntity> findByCompanyClientIdentification(String companyClientId);
}
