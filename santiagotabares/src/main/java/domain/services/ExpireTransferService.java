package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.OperationLog;
import domain.models.Transfer;
import domain.models.enums.TransferStatus;
import domain.ports.ExpireTransferPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.TransferRepositoryPort;

public class ExpireTransferService implements ExpireTransferPort {

    private final TransferRepositoryPort transferRepository;
    private final RegisterOperationLogPort auditPort;

    public ExpireTransferService(
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.transferRepository = transferRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(Transfer transfer) {

        Transfer persisted = transferRepository.findById(transfer.getId())
                .orElseThrow(() -> new BusinessException("Transfer not found: " + transfer.getId()));

        if (!persisted.isExpired()) {
            throw new BusinessException("Transfer has not expired yet");
        }

        persisted.reject();

        transferRepository.save(persisted);

        auditPort.execute(OperationLog.register(
                "EXPIRE_TRANSFER",
                transfer.getId(),
                null
        ));
    }
}
