package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Client;
import domain.models.Loan;
import domain.models.OperationLog;
import domain.ports.ClientRepositoryPort;
import domain.ports.CreateLoanRequestPort;
import domain.ports.LoanRepositoryPort;
import domain.ports.RegisterOperationLogPort;

public class CreateLoanRequestService implements CreateLoanRequestPort {

    private final ClientRepositoryPort clientRepository;
    private final LoanRepositoryPort loanRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateLoanRequestService(
            ClientRepositoryPort clientRepository,
            LoanRepositoryPort loanRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.clientRepository = clientRepository;
        this.loanRepository = loanRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(Loan loan) {

        Client client = clientRepository.findById(loan.getClientId())
                .orElseThrow(() -> new BusinessException("Client not found"));

        if (!client.isActive()) {
            throw new BusinessException("Client must be active to request a loan");
        }

        loanRepository.save(loan);

        auditPort.execute(OperationLog.register(
                "CREATE_LOAN_REQUEST",
                loan.getId(),
                null
        ));
    }
}
