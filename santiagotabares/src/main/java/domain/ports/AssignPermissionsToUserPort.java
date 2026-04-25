package domain.ports;

import domain.models.enums.SystemRole;

public interface AssignPermissionsToUserPort {

    void execute(Long userId, SystemRole role);
}
