package application.usecases;

import domain.models.Loan;
import domain.ports.CreateLoanRequestPort;
import org.springframework.stereotype.Service;

@Service
public class CreateLoanRequestUseCase {

    private final CreateLoanRequestPort createLoanRequestPort;

    public CreateLoanRequestUseCase(CreateLoanRequestPort createLoanRequestPort) {
        this.createLoanRequestPort = createLoanRequestPort;
    }

    public void execute(Loan loan) {
        createLoanRequestPort.execute(loan);
    }
}
