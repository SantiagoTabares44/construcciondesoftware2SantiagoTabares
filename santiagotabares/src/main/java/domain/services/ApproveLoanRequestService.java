package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Loan;
import domain.models.OperationLog;
import domain.ports.ApproveLoanRequestPort;
import domain.ports.LoanRepositoryPort;
import domain.ports.RegisterOperationLogPort;

import java.math.BigDecimal;
import java.util.Map;

public class ApproveLoanRequestService implements ApproveLoanRequestPort {

    private final LoanRepositoryPort loanRepository;
    private final RegisterOperationLogPort auditPort;

    public ApproveLoanRequestService(
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.loanRepository = loanRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String loanId, BigDecimal approvedAmount) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Loan not found: " + loanId));

        loan.approve(approvedAmount);

        loanRepository.save(loan);

        auditPort.execute(OperationLog.register(
                "APPROVE_LOAN",
                loanId,
                Map.of("approvedAmount", approvedAmount)
        ));
    }
}
