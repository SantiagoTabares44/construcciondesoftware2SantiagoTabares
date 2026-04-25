package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.DepositMoneyIntoAcountPort;
import domain.ports.RegisterOperationLogPort;

import java.math.BigDecimal;
import java.util.Map;

public class DepositMoneyService implements DepositMoneyIntoAcountPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final RegisterOperationLogPort auditPort;

    public DepositMoneyService(
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

        account.deposit(amount);

        bankAccountRepository.save(account);

        auditPort.execute(OperationLog.register(
                "DEPOSIT",
                accountNumber,
                Map.of("amount", amount)
        ));
    }
}
