package infrastructure.persistence.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.OperationLog;
import infrastructure.persistence.entities.OperationLogJpaEntity;

import java.util.Map;

public class OperationLogMapper {

    // Jackson convierte Map <-> JSON string
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // De entidad JPA → modelo de dominio
    public static OperationLog toDomain(OperationLogJpaEntity entity) {
        Map<String, Object> detail = null;
        if (entity.getDetail() != null) {
            try {
                detail = OBJECT_MAPPER.readValue(
                        entity.getDetail(),
                        new TypeReference<Map<String, Object>>() {}
                );
            } catch (Exception e) {
                detail = Map.of("raw", entity.getDetail());
            }
        }
        return OperationLog.reconstitute(
                entity.getOperationType(),
                entity.getProductId(),
                entity.getOperationDate(),
                detail
        );
    }

    // De modelo de dominio → entidad JPA
    public static OperationLogJpaEntity toEntity(OperationLog domain) {
        OperationLogJpaEntity entity = new OperationLogJpaEntity();
        entity.setOperationType(domain.getOperationType());
        entity.setProductId(domain.getProductId());
        entity.setOperationDate(domain.getOperationDate());

        if (domain.getDetail() != null) {
            try {
                entity.setDetail(OBJECT_MAPPER.writeValueAsString(domain.getDetail()));
            } catch (Exception e) {
                entity.setDetail("{}");
            }
        }
        return entity;
    }
}
