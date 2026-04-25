package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.NaturalPerson;
import domain.models.OperationLog;
import domain.ports.ClientRepositoryPort;
import domain.ports.CreateNaturalPersonClientPort;
import domain.ports.RegisterOperationLogPort;

public class CreateNaturalPersonClientService implements CreateNaturalPersonClientPort {

    private final ClientRepositoryPort clientRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateNaturalPersonClientService(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.clientRepository = clientRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(NaturalPerson person) {

        if (clientRepository.findById(person.getId()).isPresent()) {
            throw new BusinessException("A client with this identification already exists: " + person.getId());
        }

        clientRepository.save(person);

        auditPort.execute(OperationLog.register(
                "CREATE_NATURAL_PERSON_CLIENT",
                person.getId(),
                null
        ));
    }
}
