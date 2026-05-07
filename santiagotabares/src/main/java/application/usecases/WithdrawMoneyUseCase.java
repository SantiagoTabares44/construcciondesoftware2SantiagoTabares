package application.usecases;

import domain.ports.WithdrawMoneyFromAccountPort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class WithdrawMoneyUseCase {

    private final WithdrawMoneyFromAccountPort withdrawPort;

    public WithdrawMoneyUseCase(WithdrawMoneyFromAccountPort withdrawPort) {
        this.withdrawPort = withdrawPort;
    }

    public void execute(String accountNumber, BigDecimal amount) {
        withdrawPort.execute(accountNumber, amount);
    }
}
