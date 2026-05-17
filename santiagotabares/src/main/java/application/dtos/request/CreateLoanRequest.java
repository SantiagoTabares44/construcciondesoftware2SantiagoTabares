package application.dtos.request;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateLoanRequest {
    private String loanId;
    private String clientId;
}
