package domain.ports;

import domain.models.User;

public interface CreateUserForCompanyClientPort {

    void execute(User user);
}
