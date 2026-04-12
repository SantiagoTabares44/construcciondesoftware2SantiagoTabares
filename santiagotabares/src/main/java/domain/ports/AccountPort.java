package domain.ports;

import domain.models.BankAccount;
import java.util.*;

public interface AccountPort {
    void save(BankAccount account);
    void update(BankAccount account);
    BankAccount findByAccountNumber(String accountNumber);
    List<BankAccount>findUserAccountID(String userAccountID);
    boolean existsByAccountNumber(String accountNumber);
}
