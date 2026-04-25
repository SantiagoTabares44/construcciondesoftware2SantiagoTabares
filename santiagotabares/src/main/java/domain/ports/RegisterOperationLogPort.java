package domain.ports;

import domain.models.OperationLog;

public interface RegisterOperationLogPort {

    void execute(OperationLog log);
}
