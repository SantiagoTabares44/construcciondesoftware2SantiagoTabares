package application.dtos.response;
import domain.models.Loan;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class LoanResponse {
    private final String id;
    private final String clientId;
    private final BigDecimal approvedAmount;
    private final String status;

    private LoanResponse(String id, String clientId, BigDecimal approvedAmount, String status) {
        this.id = id;
        this.clientId = clientId;
        this.approvedAmount = approvedAmount;
        this.status = status;
    }

    public static LoanResponse from(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getClientId(),
                loan.getApprovedAmount(),
                loan.getStatus() != null ? loan.getStatus().name() : null
        );
    }
}
