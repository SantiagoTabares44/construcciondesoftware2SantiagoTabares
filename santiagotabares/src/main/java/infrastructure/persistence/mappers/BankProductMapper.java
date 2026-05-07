package infrastructure.persistence.mappers;

import domain.models.BankProduct;
import infrastructure.persistence.entities.BankProductJpaEntity;

public class BankProductMapper {

    public static BankProduct toDomain(BankProductJpaEntity entity) {
        return BankProduct.reconstitute(
                entity.getCode(),
                entity.getName(),
                false
        );
    }

    public static BankProductJpaEntity toEntity(BankProduct domain) {
        BankProductJpaEntity entity = new BankProductJpaEntity();
        entity.setCode(domain.getCode());
        entity.setName(domain.getName());
        return entity;
    }
}
