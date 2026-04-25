package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Loan;
import domain.ports.ClientRepositoryPort;
import domain.ports.ConsultClientLoansPort;
import domain.ports.LoanRepositoryPort;

import java.util.List;

public class ConsultClientLoansService implements ConsultClientLoansPort {

    private final LoanRepositoryPort loanRepository;
    private final ClientRepositoryPort clientRepository;

    public ConsultClientLoansService(
            LoanRepositoryPort loanRepository,
            ClientRepositoryPort clientRepository
    ) {
        this.loanRepository = loanRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Loan> execute(String clientId) {

        clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found: " + clientId));

        return loanRepository.findByClientId(clientId);
    }
}
