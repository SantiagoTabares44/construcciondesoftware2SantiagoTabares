package application.dtos.response;
import domain.models.Client;
import domain.models.CompanyClient;
import domain.models.NaturalPerson;
import lombok.Getter;

@Getter
public class ClientResponse {
    private final String id;
    private final String name;
    private final String type;
    private final String email;
    private final String phone;
    private final String address;
    private final String status;

    private ClientResponse(String id, String name, String type,
                           String email, String phone, String address, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public static ClientResponse from(Client client) {
        String type = client instanceof NaturalPerson ? "NATURAL_PERSON" : "COMPANY";
        String name = client instanceof NaturalPerson
                ? ((NaturalPerson) client).getFullName()
                : ((CompanyClient) client).getCompanyName();
        return new ClientResponse(
                client.getId(), name, type,
                client.getEmail(), client.getPhone(),
                client.getAddress(),
                client.getStatus() != null ? client.getStatus().name() : null
        );
    }
}
