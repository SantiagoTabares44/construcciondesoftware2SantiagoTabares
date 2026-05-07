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
    // Usado SOLO por infraestructura para reconstruir desde base de datos
    public static User reconstitute(Long id, domain.models.enums.SystemRole role, domain.models.enums.UserStatus status) {
        User u = new User();
        u.id = id;
        u.role = role;
        u.status = status;
        return u;
    }

    public domain.models.enums.SystemRole getRole() { return role; }
    private String companyClientId;

    public String getCompanyClientId() { return companyClientId; }

    // Factory para crear usuario para una empresa
    public static User createForCompany(String companyClientId, domain.models.enums.SystemRole role) {
        User u = new User();
        u.companyClientId = companyClientId;
        u.role = role;
        u.status = domain.models.enums.UserStatus.ACTIVE;
        return u;
    }
}