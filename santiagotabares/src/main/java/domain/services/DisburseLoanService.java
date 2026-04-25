package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.Loan;
import domain.models.OperationLog;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.DisburseLoanPort;
import domain.ports.LoanRepositoryPort;
import domain.ports.RegisterOperationLogPort;

import java.util.Map;

public class DisburseLoanService implements DisburseLoanPort {

    private final LoanRepositoryPort loanRepository;
    private final BankAccountRepositoryPort bankAccountRepository;
    private final RegisterOperationLogPort auditPort;

    public DisburseLoanService(
            LoanRepositoryPort loanRepository,
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.loanRepository = loanRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String loanId, String destinationAccountNumber) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Loan not found: " + loanId));

        BankAccount destination = bankAccountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new BusinessException("Destination account not found: " + destinationAccountNumber));

        loan.disburse();
        destination.deposit(loan.getApprovedAmount());

        loanRepository.save(loan);
        bankAccountRepository.save(destination);

        auditPort.execute(OperationLog.register(
                "DISBURSE_LOAN",
                loanId,
                Map.of("destinationAccount", destinationAccountNumber, "amount", loan.getApprovedAmount())
        ));
    }
}
