package domain.ports;

import java.math.BigDecimal;

public interface DepositMoneyIntoAcountPort {

    void execute(String accountNumber, BigDecimal amount);

}
