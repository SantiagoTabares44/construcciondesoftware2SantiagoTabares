package infrastructure.persistence.adapters;

import domain.models.Loan;
import domain.ports.LoanRepositoryPort;
import infrastructure.persistence.entities.ClientJpaEntity;
import infrastructure.persistence.entities.LoanJpaEntity;
import infrastructure.persistence.mappers.LoanMapper;
import infrastructure.persistence.repositories.ClientJpaRepository;
import infrastructure.persistence.repositories.LoanJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class LoanRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;
    private final ClientJpaRepository clientJpaRepository;

    public LoanRepositoryAdapter(
            LoanJpaRepository loanJpaRepository,
            ClientJpaRepository clientJpaRepository
    ) {
        this.loanJpaRepository = loanJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Optional<Loan> findById(String loanId) {
        return loanJpaRepository.findById(loanId)
                .map(LoanMapper::toDomain);
    }

    @Override
    public void save(Loan loan) {
        ClientJpaEntity clientEntity = clientJpaRepository
                .findById(loan.getClient().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Client not found in DB: " + loan.getClient().getId()
                ));
        LoanJpaEntity entity = LoanMapper.toEntity(loan, clientEntity);
        loanJpaRepository.save(entity);
    }

    @Override
    public List<Loan> findByClientId(String clientId) {
        return loanJpaRepository.findByClientIdentification(clientId)
                .stream()
                .map(LoanMapper::toDomain)
                .collect(Collectors.toList());
    }
}
