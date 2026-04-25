package domain.ports;

import domain.models.OperationLog;
import java.util.List;

public interface OperationLogRepositoryPort {
    void save(OperationLog log);
    List<OperationLog> findByProductId(String productId);
    List<OperationLog> findByAccountNumber(String accountNumber);
}
