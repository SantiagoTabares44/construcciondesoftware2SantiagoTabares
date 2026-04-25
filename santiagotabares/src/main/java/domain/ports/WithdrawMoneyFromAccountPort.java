package domain.ports;

import java.math.BigDecimal;

public interface WithdrawMoneyFromAccountPort {
    
    void execute(String accountNumber, BigDecimal amount);

}
