package domain.models;

import domain.models.enums.SystemRole;
import domain.models.enums.UserStatus;

public class User {

    private Long id;
    private SystemRole role;
    private UserStatus status;

    public void activate() {
        status = UserStatus.ACTIVE;
    }

    public void block() {
        status = UserStatus.BLOCKED;
    }

    public void assignRole(SystemRole role) {
        this.role = role;
    }
    public Long getId() { return id; }
    public domain.models.enums.UserStatus getStatus() { return status; }
}