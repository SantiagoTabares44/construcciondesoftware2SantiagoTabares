package infrastructure.persistence.mappers;

import domain.models.Transfer;
import infrastructure.persistence.entities.BankAccountJpaEntity;
import infrastructure.persistence.entities.TransferJpaEntity;

public class TransferMapper {

    // De entidad JPA → modelo de dominio
    public static Transfer toDomain(TransferJpaEntity entity) {
        return Transfer.reconstitute(
                entity.getId(),
                BankAccountMapper.toDomain(entity.getOrigin()),
                BankAccountMapper.toDomain(entity.getDestination()),
                entity.getAmount(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    // De modelo de dominio → entidad JPA
    public static TransferJpaEntity toEntity(
            Transfer domain,
            BankAccountJpaEntity originEntity,
            BankAccountJpaEntity destinationEntity
    ) {
        TransferJpaEntity entity = new TransferJpaEntity();
        entity.setId(domain.getId());
        entity.setOrigin(originEntity);
        entity.setDestination(destinationEntity);
        entity.setAmount(domain.getAmount());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
