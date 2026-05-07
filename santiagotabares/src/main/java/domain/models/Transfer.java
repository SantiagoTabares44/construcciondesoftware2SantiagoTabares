package domain.models;

import domain.Exceptions.BusinessException;
import domain.models.enums.TransferStatus;

import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDateTime;

public class Transfer {

    private String id;
    private BankAccount origin;
    private BankAccount destination;
    private BigDecimal amount;
    private TransferStatus status;
    private LocalDateTime createdAt;

    public static Transfer create(
            BankAccount origin,
            BankAccount destination,
            BigDecimal amount
    ) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Transfer amount must be greater than zero");
        }

        Transfer transfer = new Transfer();
        transfer.id = UUID.randomUUID().toString();
        transfer.origin = origin;
        transfer.destination = destination;
        transfer.amount = amount;
        transfer.status = TransferStatus.PENDING;
        transfer.createdAt = LocalDateTime.now();
        return transfer;
    }

    public boolean isExpired() {
        return createdAt.plusMinutes(60).isBefore(LocalDateTime.now());
    }

    public void markWaitingForApproval() {
        status = TransferStatus.WAITING_FOR_APPROVAL;
    }

    public void approve() {
        if (status != TransferStatus.WAITING_FOR_APPROVAL) {
            throw new BusinessException("Transfer is not waiting for approval");
        }
        status = TransferStatus.APPROVED;
    }

    public void execute() {
        origin.withdraw(amount);
        destination.deposit(amount);
        status = TransferStatus.EXECUTED;
    }

    public void reject() {
        status = TransferStatus.REJECTED;
    }
    public String getId() { return id; }
    public BankAccount getOrigin() { return origin; }
    public BankAccount getDestination() { return destination; }
    public BigDecimal getAmount() { return amount; }
    public TransferStatus getStatus() { return status; }
    // Usado SOLO por infraestructura para reconstruir desde base de datos
    public static Transfer reconstitute(
            String id,
            BankAccount origin,
            BankAccount destination,
            java.math.BigDecimal amount,
            domain.models.enums.TransferStatus status,
            java.time.LocalDateTime createdAt
    ) {
        Transfer t = new Transfer();
        t.id = id;
        t.origin = origin;
        t.destination = destination;
        t.amount = amount;
        t.status = status;
        t.createdAt = createdAt;
        return t;
    }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
}