package domain.services;

import domain.Exceptions.BusinessException;
import domain.models.BankAccount;
import domain.ports.BankAccountRepositoryPort;
import domain.ports.ConsultAccountBalancePort;

import java.math.BigDecimal;

public class ConsultAccountBalanceService implements ConsultAccountBalancePort {

    private final BankAccountRepositoryPort bankAccountRepository;

    public ConsultAccountBalanceService(BankAccountRepositoryPort bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BigDecimal execute(String accountNumber) {

        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BusinessException("Bank account not found: " + accountNumber));

        return account.getBalance();
    }
}
