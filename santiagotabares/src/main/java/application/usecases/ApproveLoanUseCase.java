package application.usecases;

import domain.ports.ApproveLoanRequestPort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ApproveLoanUseCase {

    private final ApproveLoanRequestPort approveLoanPort;

    public ApproveLoanUseCase(ApproveLoanRequestPort approveLoanPort) {
        this.approveLoanPort = approveLoanPort;
    }

    public void execute(String loanId, BigDecimal approvedAmount) {
        approveLoanPort.execute(loanId, approvedAmount);
    }
}
