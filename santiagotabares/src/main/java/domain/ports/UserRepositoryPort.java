package domain.ports;

import domain.models.User;
import java.util.Optional;
import java.util.List;

public interface UserRepositoryPort {
    Optional<User> findById(Long userId);
    void save(User user);
    List<User> findByCompanyClientId(String companyClientId);
}
