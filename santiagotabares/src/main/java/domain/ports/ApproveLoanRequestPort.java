package domain.ports;

import java.math.BigDecimal;

public interface ApproveLoanRequestPort {

    void execute(String loanId, BigDecimal approvedAmount);
}
