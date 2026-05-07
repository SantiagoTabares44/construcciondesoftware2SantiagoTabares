package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Client;
import domain.models.OperationLog;
import domain.models.User;
import domain.ports.ClientRepositoryPort;
import domain.ports.CreateUserForCompanyClientPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.UserRepositoryPort;

public class CreateUserForCompanyClientService implements CreateUserForCompanyClientPort {

    private final UserRepositoryPort userRepository;
    private final ClientRepositoryPort clientRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateUserForCompanyClientService(
            UserRepositoryPort userRepository,
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(User user) {

        userRepository.save(user);

        auditPort.execute(OperationLog.register(
                "CREATE_USER_FOR_COMPANY",
                String.valueOf(user.getId()),
                null
        ));
    }
}
