package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.OperationLog;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.ConsultAccountOperationsPort;
import domain.ports.OperationLogRepositoryPort;

import java.util.List;

public class ConsultAccountOperationsService implements ConsultAccountOperationsPort {

    private final OperationLogRepositoryPort operationLogRepository;
    private final BankAccountRepositoryPort bankAccountRepository;

    public ConsultAccountOperationsService(
            OperationLogRepositoryPort operationLogRepository,
            BankAccountRepositoryPort bankAccountRepository
    ) {
        this.operationLogRepository = operationLogRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<OperationLog> execute(String accountNumber) {

        bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Bank account not found: " + accountNumber));

        return operationLogRepository.findByAccountNumber(accountNumber);
    }
}
