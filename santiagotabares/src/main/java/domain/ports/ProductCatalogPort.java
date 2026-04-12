package domain.ports;

import domain.models.OperationLog;
import domain.models.enums.AccountType;

import java.util.*;


public interface ProductCatalogPort {
    void save(OperationLog log);
    List<OperationLog> findByAffectedProductId(String affectedProductId);
    List<OperationLog> findByUserId(int userId);
    List<OperationLog>isValidAccountType(AccountType accountType);
    List<OperationLog> findAll();
}
