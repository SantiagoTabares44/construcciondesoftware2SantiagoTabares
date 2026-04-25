package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.OperationLog;
import domain.models.Transfer;
import domain.ports.RegisterOperationLogPort;
import domain.ports.RejectTransferPort;
import domain.ports.TransferRepositoryPort;

public class RejectTransferService implements RejectTransferPort {

    private final TransferRepositoryPort transferRepository;
    private final RegisterOperationLogPort auditPort;

    public RejectTransferService(
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.transferRepository = transferRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String transferId) {

        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new BusinessException("Transfer not found: " + transferId));

        transfer.reject();

        transferRepository.save(transfer);

        auditPort.execute(OperationLog.register(
                "REJECT_TRANSFER",
                transferId,
                null
        ));
    }
}
