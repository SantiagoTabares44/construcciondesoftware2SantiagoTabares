package infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "operation_logs")
public class OperationLogJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_type", nullable = false, length = 50)
    private String operationType;

    @Column(name = "product_id", length = 50)
    private String productId;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @Column(columnDefinition = "TEXT")
    private String detail;
}
