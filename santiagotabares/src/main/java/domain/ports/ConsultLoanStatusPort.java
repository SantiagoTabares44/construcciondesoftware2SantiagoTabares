package domain.ports;

import domain.models.enums.LoanStatus;

public interface ConsultLoanStatusPort {

    LoanStatus execute(String loanId);
}
