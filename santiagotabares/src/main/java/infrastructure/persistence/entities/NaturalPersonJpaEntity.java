package infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "natural_persons")
@PrimaryKeyJoinColumn(name = "identification")
public class NaturalPersonJpaEntity extends ClientJpaEntity {

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "full_name", length = 150)
    private String fullName;
}
