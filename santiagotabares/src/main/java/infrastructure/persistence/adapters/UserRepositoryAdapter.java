package infrastructure.persistence.adapters;

import domain.models.User;
import domain.ports.UserRepositoryPort;
import infrastructure.persistence.entities.CompanyClientJpaEntity;
import infrastructure.persistence.entities.UserJpaEntity;
import infrastructure.persistence.mappers.UserMapper;
import infrastructure.persistence.repositories.ClientJpaRepository;
import infrastructure.persistence.repositories.UserJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final ClientJpaRepository clientJpaRepository;

    public UserRepositoryAdapter(
            UserJpaRepository userJpaRepository,
            ClientJpaRepository clientJpaRepository
    ) {
        this.userJpaRepository = userJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId)
                .map(UserMapper::toDomain);
    }

    @Override
    public void save(User user) {
        CompanyClientJpaEntity companyEntity = null;
        if (user.getCompanyClientId() != null) {
            companyEntity = (CompanyClientJpaEntity) clientJpaRepository
                    .findById(user.getCompanyClientId())
                    .orElseThrow(() -> new RuntimeException(
                            "Company client not found: " + user.getCompanyClientId()
                    ));
        }
        UserJpaEntity entity = UserMapper.toEntity(user, companyEntity);
        userJpaRepository.save(entity);
    }

    @Override
    public List<User> findByCompanyClientId(String companyClientId) {
        return userJpaRepository.findByCompanyClientIdentification(companyClientId)
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }
}
