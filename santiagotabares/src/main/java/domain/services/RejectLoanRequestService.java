package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Loan;
import domain.models.OperationLog;
import domain.ports.LoanRepositoryPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.RejectLoanRequestPort;

public class RejectLoanRequestService implements RejectLoanRequestPort {

    private final LoanRepositoryPort loanRepository;
    private final RegisterOperationLogPort auditPort;

    public RejectLoanRequestService(
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.loanRepository = loanRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Loan not found: " + loanId));

        loan.reject();

        loanRepository.save(loan);

        auditPort.execute(OperationLog.register(
                "REJECT_LOAN",
                loanId,
                null
        ));
    }
}
