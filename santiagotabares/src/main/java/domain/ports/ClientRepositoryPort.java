package domain.ports;

import domain.models.Client;
import java.util.Optional;
import java.util.List;

public interface ClientRepositoryPort {
    Optional<Client> findById(String clientId);
    void save(Client client);
    List<Client> findAll();
}
