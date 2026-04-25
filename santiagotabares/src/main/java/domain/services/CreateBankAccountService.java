package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.models.BankProduct;
import domain.models.Client;
import domain.ports.CreateBankAccountPort;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.ClientRepositoryPort;
import domain.ports.BankProductRepositoryPort;
import domain.ports.RegisterOperationLogPort;
import domain.services.commands.CreateBankAccountCommand;

public class CreateBankAccountService implements CreateBankAccountPort {

    private final ClientRepositoryPort clientRepository;
    private final BankAccountRepositoryPort bankAccountRepository;
    private final BankProductRepositoryPort bankProductRepository;
    private final RegisterOperationLogPort auditPort;

    public CreateBankAccountService(
            ClientRepositoryPort clientRepository,
            BankAccountRepositoryPort bankAccountRepository,
            BankProductRepositoryPort bankProductRepository,
            RegisterOperationLogPort auditPort
    ) {
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.bankProductRepository = bankProductRepository;
        this.auditPort = auditPort;
    }

    @Override
    public void execute(CreateBankAccountCommand command) {

        Client client = clientRepository.findById(command.getClientId())
                .orElseThrow(() -> new BusinessException("Client not found"));

        if (!client.isActive()) {
            throw new BusinessException("Client must be active to open a bank account");
        }

        if (bankAccountRepository.existsByAccountNumber(command.getAccountNumber())) {
            throw new BusinessException("Account number already exists");
        }

        BankProduct product = bankProductRepository.findByCode(command.getBankProductCode())
                .orElseThrow(() -> new BusinessException("Bank product not found"));

        BankAccount account = BankAccount.open(
                command.getAccountNumber(),
                client,
                product,
                command.getCurrency()
        );

        bankAccountRepository.save(account);
        auditPort.execute(
                domain.models.OperationLog.register(
                        "CREATE_BANK_ACCOUNT",
                        account.getAccountNumber(),
                        null
                )
        );
    }
}