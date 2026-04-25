package domain.ports;
import domain.models.CompanyClient;
public interface CreateCompanyClientPort {

    void execute(CompanyClient companyClient);
}
