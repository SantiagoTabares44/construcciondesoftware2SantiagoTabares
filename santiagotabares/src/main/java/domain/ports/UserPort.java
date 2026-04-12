package domain.ports;
import domain.models.*;
public interface UserPort {
     void save(User user);
    void update(User user);
    User findById(int userId);
    User findByIdentification(String identificationNumber);
    User findByUsername(String username);
    boolean existsById(int userId);
    boolean existsByIdentification(String identificationNumber);
}
