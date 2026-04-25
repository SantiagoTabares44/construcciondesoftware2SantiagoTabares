package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Client;
import domain.ports.ClientRepositoryPort;
import domain.ports.ConsultClientInformationPort;

public class ConsultClientInformationService implements ConsultClientInformationPort {

    private final ClientRepositoryPort clientRepository;

    public ConsultClientInformationService(ClientRepositoryPort clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client execute(String clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found: " + clientId));
    }
}
