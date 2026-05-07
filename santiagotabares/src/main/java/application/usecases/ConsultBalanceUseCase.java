package application.usecases;

import domain.ports.ConsultAccountBalancePort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ConsultBalanceUseCase {

    private final ConsultAccountBalancePort consultBalancePort;

    public ConsultBalanceUseCase(ConsultAccountBalancePort consultBalancePort) {
        this.consultBalancePort = consultBalancePort;
    }

    public BigDecimal execute(String accountNumber) {
        return consultBalancePort.execute(accountNumber);
    }
}
