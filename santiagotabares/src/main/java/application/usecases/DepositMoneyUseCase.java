package application.usecases;

import domain.ports.DepositMoneyIntoAcountPort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class DepositMoneyUseCase {

    private final DepositMoneyIntoAcountPort depositPort;

    public DepositMoneyUseCase(DepositMoneyIntoAcountPort depositPort) {
        this.depositPort = depositPort;
    }

    public void execute(String accountNumber, BigDecimal amount) {
        depositPort.execute(accountNumber, amount);
    }
}
