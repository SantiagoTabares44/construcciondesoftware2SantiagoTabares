package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Transfer;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.ConsultTransferHistoryPort;
import domain.ports.TransferRepositoryPort;

import java.util.List;

public class ConsultTransferHistoryService implements ConsultTransferHistoryPort {

    private final TransferRepositoryPort transferRepository;
    private final BankAccountRepositoryPort bankAccountRepository;

    public ConsultTransferHistoryService(
            TransferRepositoryPort transferRepository,
            BankAccountRepositoryPort bankAccountRepository
    ) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<Transfer> execute(String accountNumber) {

        bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Account not found: " + accountNumber));

        return transferRepository.findByAccountNumber(accountNumber);
    }
}
