package domain.services;

import domain.models.OperationLog;
import domain.ports.OperationLogRepositoryPort;
import domain.ports.RegisterOperationLogPort;

public class RegisterOperationLogService implements RegisterOperationLogPort {

    private final OperationLogRepositoryPort operationLogRepository;

    public RegisterOperationLogService(OperationLogRepositoryPort operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Override
    public void execute(OperationLog log) {
        operationLogRepository.save(log);
    }
}
