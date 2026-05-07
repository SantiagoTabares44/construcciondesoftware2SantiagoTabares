package infrastructure.persistence.mappers;

import domain.models.Client;
import domain.models.CompanyClient;
import domain.models.NaturalPerson;
import infrastructure.persistence.entities.ClientJpaEntity;
import infrastructure.persistence.entities.CompanyClientJpaEntity;
import infrastructure.persistence.entities.NaturalPersonJpaEntity;

public class ClientMapper {

    public static Client toDomain(ClientJpaEntity entity) {
        if (entity instanceof NaturalPersonJpaEntity np) {
            NaturalPerson person = NaturalPerson.create(
                    np.getIdentification(),
                    np.getFullName() != null ? np.getFullName() : np.getName(),
                    np.getBirthDate()
            );
            if (np.getEmail() != null) {
                person.updateContactInfo(np.getEmail(), np.getPhone(), np.getAddress());
            }
            return person;
        }
        if (entity instanceof CompanyClientJpaEntity co) {
            CompanyClient company = CompanyClient.create(co.getTaxId(), co.getCompanyName());
            if (co.getEmail() != null) {
                company.updateContactInfo(co.getEmail(), co.getPhone(), co.getAddress());
            }
            return company;
        }
        throw new IllegalArgumentException("Unknown client type: " + entity.getClass());
    }

    public static ClientJpaEntity toEntity(Client domain) {
        if (domain instanceof NaturalPerson np) {
            NaturalPersonJpaEntity entity = new NaturalPersonJpaEntity();
            entity.setIdentification(np.getId());
            entity.setName(np.getFullName() != null ? np.getFullName() : np.getId());
            entity.setFullName(np.getFullName());
            entity.setBirthDate(np.birthDate());
            entity.setEmail(np.getEmail());
            entity.setPhone(np.getPhone());
            entity.setAddress(np.getAddress());
            entity.setStatus(np.getStatus());
            return entity;
        }
        if (domain instanceof CompanyClient co) {
            CompanyClientJpaEntity entity = new CompanyClientJpaEntity();
            entity.setIdentification(co.getId());
            entity.setName(co.getCompanyName());
            entity.setTaxId(co.getTaxId());
            entity.setCompanyName(co.getCompanyName());
            entity.setEmail(co.getEmail());
            entity.setPhone(co.getPhone());
            entity.setAddress(co.getAddress());
            entity.setStatus(co.getStatus());
            return entity;
        }
        throw new IllegalArgumentException("Unknown client type: " + domain.getClass());
    }
}
