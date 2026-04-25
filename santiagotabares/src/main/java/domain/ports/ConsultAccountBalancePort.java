package domain.ports;

import java.math.BigDecimal;

public interface ConsultAccountBalancePort {
    BigDecimal execute(String accountNumber);
}

