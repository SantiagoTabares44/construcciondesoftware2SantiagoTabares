package application.dtos.request;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCompanyClientRequest {
    private String taxId;
    private String companyName;
    private String email;
    private String phone;
    private String address;
}
