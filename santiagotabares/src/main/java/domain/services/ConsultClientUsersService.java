package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.User;
import domain.ports.ClientRepositoryPort;
import domain.ports.ConsultClientUsersPort;
import domain.ports.UserRepositoryPort;

import java.util.List;

public class ConsultClientUsersService implements ConsultClientUsersPort {

    private final UserRepositoryPort userRepository;
    private final ClientRepositoryPort clientRepository;

    public ConsultClientUsersService(
            UserRepositoryPort userRepository,
            ClientRepositoryPort clientRepository
    ) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<User> execute(String companyId) {

        clientRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException("Company client not found: " + companyId));

        return userRepository.findByCompanyClientId(companyId);
    }
}
