package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.ClientRepositoryPort;
import domain.ports.ConsultClientAccountsPort;

import java.util.List;

public class ConsultClientAccountsService implements ConsultClientAccountsPort {

    private final BankAccountRepositoryPort bankAccountRepository;
    private final ClientRepositoryPort clientRepository;

    public ConsultClientAccountsService(
            BankAccountRepositoryPort bankAccountRepository,
            ClientRepositoryPort clientRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<BankAccount> execute(String clientId) {

        clientRepository.findById(clientId)
                .orElseThrow(() -> new BusinessException("Client not found: " + clientId));

        return bankAccountRepository.findByClientId(clientId);
    }
}
