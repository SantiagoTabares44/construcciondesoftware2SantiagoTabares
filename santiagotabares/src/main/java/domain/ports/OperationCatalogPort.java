package domain.ports;

import domain.models.OperationLog;
import java.util.*;

public interface OperationCatalogPort {

    void save(OperationLog log);
    List<OperationLog> findByAffectedProductId(String affectedProductId);
    List<OperationLog> findByUserId(int userId);
    List<OperationLog> findAll();
}
