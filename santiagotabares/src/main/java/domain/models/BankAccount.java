package domain.models;

import domain.Exceptions.BusinessException;
import domain.models.enums.AccountStatus;

import java.math.BigDecimal;

public class BankAccount {

    private String accountNumber;
    private Client owner;
    private BankProduct product;
    private BigDecimal balance;
    private AccountStatus status;
    private String currency;

    public static BankAccount open(
            String accountNumber,
            Client owner,
            BankProduct product,
            String currency
    ) {
        BankAccount account = new BankAccount();
        account.accountNumber = accountNumber;
        account.owner = owner;
        account.product = product;
        account.currency = currency;
        account.balance = BigDecimal.ZERO;
        account.status = AccountStatus.ACTIVE;
        return account;
    }

    public void deposit(BigDecimal amount) {
        validateOperative();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El valor a depositar debe ser mayor a 0.");
        }
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateOperative();
        if (balance.compareTo(amount) < 0) {
            throw new BusinessException("Fondos insuficientes.");
        }
        balance = balance.subtract(amount);
    }

    public void block() {
        status = AccountStatus.BLOCKED;
    }

    private void validateOperative() {
        if (status != AccountStatus.ACTIVE) {
            throw new BusinessException("Esta cuenta no esta activa.");
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    // Usado SOLO por la capa de infraestructura para reconstruir
    // el objeto desde base de datos, sin ejecutar reglas de negocio.
    public static BankAccount reconstitute(
            String accountNumber,
            Client owner,
            java.math.BigDecimal balance,
            domain.models.enums.AccountStatus status,
            String currency
    ) {
        BankAccount account = new BankAccount();
        account.accountNumber = accountNumber;
        account.owner = owner;
        account.balance = balance;
        account.status = status;
        account.currency = currency;
        return account;
    }

    public domain.models.enums.AccountStatus getStatus() { return status; }
    public String getCurrency() { return currency; }
    public Client getOwner() { return owner; }
    public BankProduct getProduct() { return product; }

    public static BankAccount stub(String accountNumber) {
    BankAccount account = new BankAccount();
    account.accountNumber = accountNumber;
    return account;
}

}