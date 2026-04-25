package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.CompanyClient;
import domain.models.OperationLog;
import domain.ports.ClientRepositoryPort;
import domain.ports.CreateCompanyClientPort;
import domain.ports.RegisterOperationLogPort;

public class CreateCompanyClientService implements CreateCompanyClientPort {

    private final ClientRepositoryPort clientRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateCompanyClientService(
            ClientRepositoryPort clientRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.clientRepository = clientRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(CompanyClient companyClient) {

        if (clientRepository.findById(companyClient.getId()).isPresent()) {
            throw new BusinessException("A client with this tax ID already exists: " + companyClient.getId());
        }

        clientRepository.save(companyClient);

        auditPort.execute(OperationLog.register(
                "CREATE_COMPANY_CLIENT",
                companyClient.getId(),
                null
        ));
    }
}
