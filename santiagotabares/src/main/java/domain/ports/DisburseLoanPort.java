package domain.ports;

public interface DisburseLoanPort {

    void execute(String loanId, String destinationAccountNumber);

}
