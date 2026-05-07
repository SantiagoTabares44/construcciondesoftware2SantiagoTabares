package domain.models;

import domain.models.enums.*;

public class CompanyClient extends Client{

    private String companyName;
    private String taxId;

    public static CompanyClient create(String taxId, String companyName){
        CompanyClient company = new CompanyClient();
        company.identification = taxId;
        company.taxId = taxId;
        company.companyName = companyName;
        company.status = UserStatus.ACTIVE;

        
        return company;
    }
    public String getCompanyName() { return companyName; }
    public String getTaxId() { return taxId; }
}