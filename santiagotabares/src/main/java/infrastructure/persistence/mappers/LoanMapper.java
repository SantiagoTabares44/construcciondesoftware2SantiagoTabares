package infrastructure.persistence.mappers;

import domain.models.Loan;
import infrastructure.persistence.entities.ClientJpaEntity;
import infrastructure.persistence.entities.LoanJpaEntity;

public class LoanMapper {

    // De entidad JPA → modelo de dominio
    public static Loan toDomain(LoanJpaEntity entity) {
        return Loan.reconstitute(
                entity.getId(),
                ClientMapper.toDomain(entity.getClient()),
                entity.getApprovedAmount(),
                entity.getStatus()
        );
    }

    // De modelo de dominio → entidad JPA
    public static LoanJpaEntity toEntity(Loan domain, ClientJpaEntity clientEntity) {
        LoanJpaEntity entity = new LoanJpaEntity();
        entity.setId(domain.getId());
        entity.setClient(clientEntity);
        entity.setApprovedAmount(domain.getApprovedAmount());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}
