package domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyClient extends Client{

    private String companyName;
    private String taxId;

    public CompanyClient(String name,String identification, String email, String phone, String address,String companyName,String taxId) {
        super(name,identification, email, phone, address);
    }


}
