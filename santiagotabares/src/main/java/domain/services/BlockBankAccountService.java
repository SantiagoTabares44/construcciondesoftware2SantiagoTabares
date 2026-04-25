package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.OperationLog;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.BlockBankAccountPort;
import domain.ports.RegisterOperationLogPort;

public class BlockBankAccountService implements BlockBankAccountPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final RegisterOperationLogPort auditPort;

    public BlockBankAccountService(
            BankAccountRepositoryPort bankAccountRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(String accountNumber) {

        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Bank account not found: " + accountNumber));

        account.block();

        bankAccountRepository.save(account);

        auditPort.execute(OperationLog.register(
                "BLOCK_BANK_ACCOUNT",
                accountNumber,
                null
        ));
    }
}
