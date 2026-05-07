package infrastructure.persistence.adapters;

import domain.models.OperationLog;
import domain.ports.OperationLogRepositoryPort;
import infrastructure.persistence.mappers.OperationLogMapper;
import infrastructure.persistence.repositories.OperationLogJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class OperationLogRepositoryAdapter implements OperationLogRepositoryPort {

    private final OperationLogJpaRepository operationLogJpaRepository;

    public OperationLogRepositoryAdapter(OperationLogJpaRepository operationLogJpaRepository) {
        this.operationLogJpaRepository = operationLogJpaRepository;
    }

    @Override
    public void save(OperationLog log) {
        operationLogJpaRepository.save(OperationLogMapper.toEntity(log));
    }

    @Override
    public List<OperationLog> findByProductId(String productId) {
        return operationLogJpaRepository.findByProductId(productId)
                .stream()
                .map(OperationLogMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationLog> findByAccountNumber(String accountNumber) {
        // Los logs de una cuenta tienen su número en el productId
        return operationLogJpaRepository.findByProductIdStartingWith(accountNumber)
                .stream()
                .map(OperationLogMapper::toDomain)
                .collect(Collectors.toList());
    }
}
