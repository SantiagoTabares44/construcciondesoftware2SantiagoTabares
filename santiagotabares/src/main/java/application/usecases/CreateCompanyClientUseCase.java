package application.usecases;

import domain.models.CompanyClient;
import domain.ports.CreateCompanyClientPort;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyClientUseCase {

    private final CreateCompanyClientPort createCompanyClientPort;

    public CreateCompanyClientUseCase(CreateCompanyClientPort createCompanyClientPort) {
        this.createCompanyClientPort = createCompanyClientPort;
    }

    public void execute(CompanyClient company) {
        createCompanyClientPort.execute(company);
    }
}
