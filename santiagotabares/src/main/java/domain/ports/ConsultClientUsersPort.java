package domain.ports;

import java.util.List;

import domain.models.User;

public interface ConsultClientUsersPort {

    List<User> execute(String companyId);
}
