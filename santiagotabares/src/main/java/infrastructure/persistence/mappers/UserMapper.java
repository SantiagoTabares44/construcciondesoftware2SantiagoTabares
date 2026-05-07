package infrastructure.persistence.mappers;

import domain.models.User;
import infrastructure.persistence.entities.CompanyClientJpaEntity;
import infrastructure.persistence.entities.UserJpaEntity;

public class UserMapper {

    // De entidad JPA → modelo de dominio
    public static User toDomain(UserJpaEntity entity) {
        return User.reconstitute(
                entity.getId(),
                entity.getRole(),
                entity.getStatus()
        );
    }

    // De modelo de dominio → entidad JPA
    public static UserJpaEntity toEntity(User domain, CompanyClientJpaEntity companyEntity) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId());
        entity.setRole(domain.getRole());
        entity.setStatus(domain.getStatus());
        entity.setCompanyClient(companyEntity);
        return entity;
    }
}
