package infrastructure.persistence.adapters;

import domain.models.BankProduct;
import domain.ports.BankProductRepositoryPort;
import infrastructure.persistence.mappers.BankProductMapper;
import infrastructure.persistence.repositories.BankProductJpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public class BankProductRepositoryAdapter implements BankProductRepositoryPort {

    private final BankProductJpaRepository jpaRepository;

    public BankProductRepositoryAdapter(BankProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<BankProduct> findByCode(String code) {
        return jpaRepository.findById(code)
                .map(BankProductMapper::toDomain);
    }
}
