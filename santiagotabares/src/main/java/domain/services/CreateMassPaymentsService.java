package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.models.Transfer;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.CreateMassPaymentsPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.TransferRepositoryPort;

import java.util.List;
import java.util.Map;

public class CreateMassPaymentsService implements CreateMassPaymentsPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final TransferRepositoryPort transferRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateMassPaymentsService(
            BankAccountRepositoryPort bankAccountRepository,
            TransferRepositoryPort transferRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.transferRepository = transferRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(List<Transfer> transfers) {

        if (transfers == null || transfers.isEmpty()) {
            throw new BusinessException("Transfer list cannot be empty");
        }

        for (Transfer transfer : transfers) {

            BankAccount origin = bankAccountRepository
                    .findByAccountNumber(transfer.getOrigin().getAccountNumber())
                    .orElseThrow(() -> new BusinessException(
                            "Origin account not found: " + transfer.getOrigin().getAccountNumber()));

            BankAccount destination = bankAccountRepository
                    .findByAccountNumber(transfer.getDestination().getAccountNumber())
                    .orElseThrow(() -> new BusinessException(
                            "Destination account not found: " + transfer.getDestination().getAccountNumber()));

            Transfer newTransfer = Transfer.create(origin, destination, transfer.getAmount());
            newTransfer.execute();

            bankAccountRepository.save(origin);
            bankAccountRepository.save(destination);
            transferRepository.save(newTransfer);

            auditPort.execute(OperationLog.register(
                    "MASS_PAYMENT",
                    newTransfer.getId(),
                    Map.of(
                            "origin", origin.getAccountNumber(),
                            "destination", destination.getAccountNumber(),
                            "amount", newTransfer.getAmount()
                    )
            ));
        }
    }
}
