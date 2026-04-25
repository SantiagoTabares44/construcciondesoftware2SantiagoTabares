package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.OperationLog;
import domain.models.User;
import domain.ports.ActivateUserPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.UserRepositoryPort;


public class ActivateUserService implements ActivateUserPort {

    private final UserRepositoryPort userRepository;
    private final RegisterOperationLogPort auditPort;

    public ActivateUserService(
            UserRepositoryPort userRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.userRepository = userRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found: " + userId));

        user.activate();

        userRepository.save(user);

        auditPort.execute(OperationLog.register(
                "ACTIVATE_USER",
                String.valueOf(userId),
                null
        ));
    }
}
