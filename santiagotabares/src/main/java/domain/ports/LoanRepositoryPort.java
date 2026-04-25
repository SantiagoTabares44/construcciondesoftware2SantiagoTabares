package domain.ports;

import domain.models.Loan;
import java.util.Optional;
import java.util.List;

public interface LoanRepositoryPort {
    Optional<Loan> findById(String loanId);
    void save(Loan loan);
    List<Loan> findByClientId(String clientId);
}
