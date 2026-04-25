package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.models.Transfer;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.CreateTransferPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.TransferRepositoryPort;

import java.util.Map;

public class CreateTransferService implements CreateTransferPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final TransferRepositoryPort transferRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateTransferService(
            BankAccountRepositoryPort bankAccountRepository,
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.transferRepository = transferRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(Transfer transfer) {

        BankAccount origin = bankAccountRepository
                .findByAccountNumber(transfer.getOrigin().getAccountNumber())
                .orElseThrow(() -> new BusinessException("Origin account not found"));

        BankAccount destination = bankAccountRepository
                .findByAccountNumber(transfer.getDestination().getAccountNumber())
                .orElseThrow(() -> new BusinessException("Destination account not found"));

        Transfer newTransfer = Transfer.create(origin, destination, transfer.getAmount());

        transferRepository.save(newTransfer);

        auditPort.execute(OperationLog.register(
                "CREATE_TRANSFER",
                newTransfer.getId(),
                Map.of(
                        "origin", origin.getAccountNumber(),
                        "destination", destination.getAccountNumber(),
                        "amount", newTransfer.getAmount()
                )
        ));
    }
}
