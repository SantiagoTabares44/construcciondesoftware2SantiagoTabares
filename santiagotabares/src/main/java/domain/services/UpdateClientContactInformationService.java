package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.Client;
import domain.models.OperationLog;
import domain.ports.ClientRepositoryPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.UpdateClientContactInformationPort;

import java.util.Map;

public class UpdateClientContactInformationService implements UpdateClientContactInformationPort {

    private final ClientRepositoryPort clientRepository;
    private final RegisterOperationLogPort auditPort;

    public UpdateClientContactInformationService(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.clientRepository = clientRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String clientId, String email, String phone, String address) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found: " + clientId));

        client.updateContactInfo(email, phone, address);

        clientRepository.save(client);

        auditPort.execute(OperationLog.register(
                "UPDATE_CLIENT_CONTACT",
                clientId,
                Map.of("email", email, "phone", phone, "address", address)
        ));
    }
}
