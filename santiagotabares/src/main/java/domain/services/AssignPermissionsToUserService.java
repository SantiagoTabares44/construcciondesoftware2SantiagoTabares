package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.OperationLog;
import domain.models.User;
import domain.models.enums.SystemRole;
import domain.ports.AssignPermissionsToUserPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.UserRepositoryPort;

import java.util.Map;

public class AssignPermissionsToUserService implements AssignPermissionsToUserPort {

    private final UserRepositoryPort userRepository;
    private final RegisterOperationLogPort auditPort;

    public AssignPermissionsToUserService(
            UserRepositoryPort userRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.userRepository = userRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(Long userId, SystemRole role) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found: " + userId));

        user.assignRole(role);

        userRepository.save(user);

        auditPort.execute(OperationLog.register(
                "ASSIGN_ROLE",
                String.valueOf(userId),
                Map.of("role", role.name())
        ));
    }
}
