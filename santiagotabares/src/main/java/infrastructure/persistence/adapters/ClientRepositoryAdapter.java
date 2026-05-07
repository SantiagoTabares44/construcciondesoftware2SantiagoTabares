package infrastructure.persistence.adapters;

import domain.models.Client;
import domain.ports.ClientRepositoryPort;
import infrastructure.persistence.entities.ClientJpaEntity;
import infrastructure.persistence.mappers.ClientMapper;
import infrastructure.persistence.repositories.ClientJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Repository le dice a Spring que esta clase es un componente de persistencia
// Spring la detecta automáticamente al arrancar
@org.springframework.stereotype.Repository
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    // Spring inyecta el repositorio JPA automáticamente
    private final ClientJpaRepository jpaRepository;

    public ClientRepositoryAdapter(ClientJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Client> findById(String clientId) {
        // 1. Busca en la BD usando Spring Data → devuelve Optional<ClientJpaEntity>
        // 2. Si encuentra algo, usa el mapper para convertirlo a modelo de dominio
        return jpaRepository.findById(clientId)
                .map(ClientMapper::toDomain);
    }

    @Override
    public void save(Client client) {
        // 1. Convierte el modelo de dominio a entidad JPA
        // 2. Spring Data guarda (INSERT o UPDATE automáticamente)
        ClientJpaEntity entity = ClientMapper.toEntity(client);
        jpaRepository.save(entity);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(ClientMapper::toDomain)
                .collect(Collectors.toList());
    }
}
