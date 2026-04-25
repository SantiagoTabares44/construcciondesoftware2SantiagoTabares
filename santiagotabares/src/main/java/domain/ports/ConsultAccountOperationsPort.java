package domain.ports;

import java.util.List;

import domain.models.OperationLog;

public interface ConsultAccountOperationsPort {

    List<OperationLog> execute(String accountNumber);
}
