package domain.ports;

import domain.models.Loan;

public interface CreateLoanRequestPort {

    void execute(Loan loan);
}
