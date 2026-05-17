package application.dtos.response;
import domain.models.BankAccount;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class BankAccountResponse {
    private final String accountNumber;
    private final String clientId;
    private final BigDecimal balance;
    private final String status;
    private final String currency;

    private BankAccountResponse(String accountNumber, String clientId,
                                BigDecimal balance, String status, String currency) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.balance = balance;
        this.status = status;
        this.currency = currency;
    }

    public static BankAccountResponse from(BankAccount account) {
        return new BankAccountResponse(
                account.getAccountNumber(),
                account.getOwner() != null ? account.getOwner().getId() : null,
                account.getBalance(),
                account.getStatus() != null ? account.getStatus().name() : null,
                account.getCurrency()
        );
    }
}
