package infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_clients")
@PrimaryKeyJoinColumn(name = "identification")
public class CompanyClientJpaEntity extends ClientJpaEntity {

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "tax_id", nullable = false, length = 50)
    private String taxId;
}
