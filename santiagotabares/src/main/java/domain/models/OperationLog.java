package domain.models;

import java.time.LocalDateTime;
import java.util.Map;

public class OperationLog {

    private String operationType;
    private String productId;
    private LocalDateTime operationDate;
    private Map<String, Object> detail;

    public static OperationLog register(
            String operationType,
            String productId,
            Map<String, Object> detail
    ) {
        OperationLog log = new OperationLog();
        log.operationType = operationType;
        log.productId = productId;
        log.detail = detail;
        log.operationDate = LocalDateTime.now();
        return log;
    }
    // Usado SOLO por infraestructura para reconstruir desde base de datos
    public static OperationLog reconstitute(
            String operationType,
            String productId,
            java.time.LocalDateTime operationDate,
            java.util.Map<String, Object> detail
    ) {
        OperationLog log = new OperationLog();
        log.operationType = operationType;
        log.productId = productId;
        log.operationDate = operationDate;
        log.detail = detail;
        return log;
    }

    public String getOperationType() { return operationType; }
    public String getProductId() { return productId; }
    public java.time.LocalDateTime getOperationDate() { return operationDate; }
    public java.util.Map<String, Object> getDetail() { return detail; }
}