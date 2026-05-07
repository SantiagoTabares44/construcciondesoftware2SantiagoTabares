package infrastructure.persistence.adapters;

import domain.models.BankAccount;
import domain.ports.BankAccountRepositoryPort;
import infrastructure.persistence.entities.BankAccountJpaEntity;
import infrastructure.persistence.entities.ClientJpaEntity;
import infrastructure.persistence.mappers.BankAccountMapper;
import infrastructure.persistence.repositories.BankAccountJpaRepository;
import infrastructure.persistence.repositories.BankProductJpaRepository;
import infrastructure.persistence.repositories.ClientJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class BankAccountRepositoryAdapter implements BankAccountRepositoryPort {

    private final BankAccountJpaRepository bankAccountJpaRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final BankProductJpaRepository bankProductJpaRepository;

    public BankAccountRepositoryAdapter(
            BankAccountJpaRepository bankAccountJpaRepository,
            ClientJpaRepository clientJpaRepository,
            BankProductJpaRepository bankProductJpaRepository
    ) {
        this.bankAccountJpaRepository = bankAccountJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
        this.bankProductJpaRepository = bankProductJpaRepository;
    }

    @Override
    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return bankAccountJpaRepository.findById(accountNumber)
                .map(BankAccountMapper::toDomain);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return bankAccountJpaRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public void save(BankAccount account) {
        ClientJpaEntity ownerEntity = clientJpaRepository
                .findById(account.getOwner().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Owner not found in DB: " + account.getOwner().getId()
                ));
        infrastructure.persistence.entities.BankProductJpaEntity productEntity = null;
        if (account.getProduct() != null) {
            productEntity = bankProductJpaRepository
                    .findById(account.getProduct().getCode())
                    .orElse(null);
        }
        BankAccountJpaEntity entity = BankAccountMapper.toEntity(account, ownerEntity, productEntity);
        bankAccountJpaRepository.save(entity);
    }

    @Override
    public List<BankAccount> findByClientId(String clientId) {
        return bankAccountJpaRepository.findByOwnerIdentification(clientId)
                .stream()
                .map(BankAccountMapper::toDomain)
                .collect(Collectors.toList());
    }
}
