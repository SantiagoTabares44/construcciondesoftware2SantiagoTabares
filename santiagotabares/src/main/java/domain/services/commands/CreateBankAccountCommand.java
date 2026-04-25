package domain.services.commands;

public class CreateBankAccountCommand {

    private final String accountNumber;
    private final String clientId;
    private final String bankProductCode;
    private final String currency;

    public CreateBankAccountCommand(
            String accountNumber,
            String clientId,
            String bankProductCode,
            String currency
    ) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.bankProductCode = bankProductCode;
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public String getBankProductCode() {
        return bankProductCode;
    }

    public String getCurrency() {
        return currency;
    }
}