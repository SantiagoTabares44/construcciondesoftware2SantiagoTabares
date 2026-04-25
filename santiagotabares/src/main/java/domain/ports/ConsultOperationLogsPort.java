package domain.ports;

import java.util.List;

import domain.models.OperationLog;

public interface ConsultOperationLogsPort {

    List<OperationLog> findByProductId(String productId);
}
