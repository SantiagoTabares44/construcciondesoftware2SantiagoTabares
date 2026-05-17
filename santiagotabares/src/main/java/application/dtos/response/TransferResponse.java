package application.dtos.response;
import domain.models.Transfer;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class TransferResponse {
    private final String id;
    private final String originAccount;
    private final String destinationAccount;
    private final BigDecimal amount;
    private final String status;
    private final LocalDateTime createdAt;

    private TransferResponse(String id, String originAccount, String destinationAccount,
                             BigDecimal amount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
                transfer.getId(),
                transfer.getOrigin() != null ? transfer.getOrigin().getAccountNumber() : null,
                transfer.getDestination() != null ? transfer.getDestination().getAccountNumber() : null,
                transfer.getAmount(),
                transfer.getStatus() != null ? transfer.getStatus().name() : null,
                transfer.getCreatedAt()
        );
    }
}
