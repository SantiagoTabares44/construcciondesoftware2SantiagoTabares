package infrastructure.persistence.adapters;

import domain.models.Transfer;
import domain.ports.TransferRepositoryPort;
import infrastructure.persistence.entities.BankAccountJpaEntity;
import infrastructure.persistence.entities.TransferJpaEntity;
import infrastructure.persistence.mappers.TransferMapper;
import infrastructure.persistence.repositories.BankAccountJpaRepository;
import infrastructure.persistence.repositories.TransferJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class TransferRepositoryAdapter implements TransferRepositoryPort {

    private final TransferJpaRepository transferJpaRepository;
    private final BankAccountJpaRepository bankAccountJpaRepository;

    public TransferRepositoryAdapter(
            TransferJpaRepository transferJpaRepository,
            BankAccountJpaRepository bankAccountJpaRepository
    ) {
        this.transferJpaRepository = transferJpaRepository;
        this.bankAccountJpaRepository = bankAccountJpaRepository;
    }

    @Override
    public Optional<Transfer> findById(String transferId) {
        return transferJpaRepository.findById(transferId)
                .map(TransferMapper::toDomain);
    }

    @Override
    public void save(Transfer transfer) {
        BankAccountJpaEntity originEntity = bankAccountJpaRepository
                .findById(transfer.getOrigin().getAccountNumber())
                .orElseThrow(() -> new RuntimeException(
                        "Origin account not found: " + transfer.getOrigin().getAccountNumber()
                ));
        BankAccountJpaEntity destinationEntity = bankAccountJpaRepository
                .findById(transfer.getDestination().getAccountNumber())
                .orElseThrow(() -> new RuntimeException(
                        "Destination account not found: " + transfer.getDestination().getAccountNumber()
                ));
        TransferJpaEntity entity = TransferMapper.toEntity(transfer, originEntity, destinationEntity);
        transferJpaRepository.save(entity);
    }

    @Override
    public List<Transfer> findByAccountNumber(String accountNumber) {
        return transferJpaRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(TransferMapper::toDomain)
                .collect(Collectors.toList());
    }
}
