package application.dtos.request;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateBankAccountRequest {
    private String accountNumber;
    private String clientId;
    private String bankProductCode;
    private String currency;
}
