package application.dtos.request;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class CreateTransferRequest {
    private String originAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
}
