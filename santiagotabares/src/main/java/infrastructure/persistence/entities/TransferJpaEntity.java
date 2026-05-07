package infrastructure.persistence.entities;

import domain.models.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transfers")
public class TransferJpaEntity {

    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_account", nullable = false)
    private BankAccountJpaEntity origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account", nullable = false)
    private BankAccountJpaEntity destination;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransferStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
