package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.models.Transfer;
import domain.ports.ApproveTransferPort;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.TransferRepositoryPort;

import java.util.Map;

public class ApproveTransferService implements ApproveTransferPort {

    private final TransferRepositoryPort transferRepository;
    private final BankAccountRepositoryPort bankAccountRepository;
    private final RegisterOperationLogPort auditPort;

    public ApproveTransferService(
            TransferRepositoryPort transferRepository,
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.transferRepository = transferRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String transferId) {

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new BusinessException("Transfer not found: " + transferId));

        if (transfer.isExpired()) {
            transfer.reject();
            transferRepository.save(transfer);
            throw new BusinessException("Transfer has expired and cannot be approved");
        }

        transfer.approve();
        transfer.execute();

        bankAccountRepository.save(transfer.getOrigin());
        bankAccountRepository.save(transfer.getDestination());
        transferRepository.save(transfer);

        auditPort.execute(OperationLog.register(
                "APPROVE_TRANSFER",
                transferId,
                Map.of("amount", transfer.getAmount())
        ));
    }
}
