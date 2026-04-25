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
}