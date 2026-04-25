package domain.ports;

public interface RejectLoanRequestPort {

    void execute(String loanId);
}
