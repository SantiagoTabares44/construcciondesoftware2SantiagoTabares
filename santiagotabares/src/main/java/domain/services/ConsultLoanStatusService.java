package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Loan;
import domain.models.enums.LoanStatus;
import domain.ports.ConsultLoanStatusPort;
import domain.ports.LoanRepositoryPort;

public class ConsultLoanStatusService implements ConsultLoanStatusPort {

    private final LoanRepositoryPort loanRepository;

    public ConsultLoanStatusService(LoanRepositoryPort loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public LoanStatus execute(String loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Loan not found: " + loanId));

        return loan.getStatus();
    }
}
