package domain.ports;

import java.util.List;

import domain.models.BankAccount;

public interface ConsultClientAccountsPort {

    List<BankAccount> execute(String clientId);
}
