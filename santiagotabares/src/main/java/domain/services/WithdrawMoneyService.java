package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.RegisterOperationLogPort;
import domain.ports.WithdrawMoneyFromAccountPort;

import java.math.BigDecimal;
import java.util.Map;

public class WithdrawMoneyService implements WithdrawMoneyFromAccountPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final RegisterOperationLogPort auditPort;

    public WithdrawMoneyService(
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String accountNumber, BigDecimal amount) {

        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Bank account not found: " + accountNumber));

        account.withdraw(amount);

        bankAccountRepository.save(account);

        auditPort.execute(OperationLog.register(
                "WITHDRAWAL",
                accountNumber,
                Map.of("amount", amount)
        ));
    }
}
