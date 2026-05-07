package infrastructure.persistence.mappers;

import domain.models.BankAccount;
import infrastructure.persistence.entities.BankAccountJpaEntity;
import infrastructure.persistence.entities.BankProductJpaEntity;
import infrastructure.persistence.entities.ClientJpaEntity;

public class BankAccountMapper {

    public static BankAccount toDomain(BankAccountJpaEntity entity) {
        return BankAccount.reconstitute(
                entity.getAccountNumber(),
                ClientMapper.toDomain(entity.getOwner()),
                entity.getBalance(),
                entity.getStatus(),
                entity.getCurrency()
        );
    }

    public static BankAccountJpaEntity toEntity(BankAccount domain, ClientJpaEntity ownerEntity, BankProductJpaEntity bankProductEntity) {
        BankAccountJpaEntity entity = new BankAccountJpaEntity();
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setOwner(ownerEntity);
        entity.setBalance(domain.getBalance());
        entity.setStatus(domain.getStatus());
        entity.setCurrency(domain.getCurrency());
        entity.setBankProduct(bankProductEntity);
        return entity;
    }
}
