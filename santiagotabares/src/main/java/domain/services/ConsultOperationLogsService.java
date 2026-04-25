package domain.services;

import domain.models.OperationLog;
import domain.ports.ConsultOperationLogsPort;
import domain.ports.OperationLogRepositoryPort;

import java.util.List;

public class ConsultOperationLogsService implements ConsultOperationLogsPort {

    private final OperationLogRepositoryPort operationLogRepository;

    public ConsultOperationLogsService(OperationLogRepositoryPort operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Override
    public List<OperationLog> findByProductId(String productId) {
        return operationLogRepository.findByProductId(productId);
    }
}
