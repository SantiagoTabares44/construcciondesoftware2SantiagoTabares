package domain.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.models.BankAccount;
import domain.models.User;
import domain.models.enums.SystemRole;
import domain.models.enums.UserStatus;
import domain.Exceptions.*;

import domain.ports.*;

public class CreateBankAccount {

    private AccountPort accountPort;
    private UserPort userPort;
    private ProductCatalogPort productCatalogPort;
    private OperationCatalogPort  opLogPort;

    private  DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Constructor
    public CreateBankAccount(AccountPort accountPort,
        UserPort userPort,
        ProductCatalogPort productCatalogPort,
        OperationCatalogPort oplogPort){
        this.accountPort = accountPort;
        this.userPort = userPort;
        this.productCatalogPort = productCatalogPort;
        this.opLogPort = oplogPort;
    }

    public BankAccount execute(BankAccount account, User requestingUser){
        validateRequestingUser(requestingUser);
        validateAccount(account);
        validateUniqueAccountNumber(account.getAccountNumber());

        User userID = validateAndGetHolder(account.getUserAccountID());
        validateAccountType(account);

        accountPort.save(account);
        registerAuditLog(account, requestingUser, userID);

        return account;
    }

    private void validateRequestingUser(User requestingUser) {
        if (requestingUser == null) {
            throw new BusinessException("El usuario que realiza la operación es obligatorio");
        }

        if (requestingUser.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("El usuario que realiza la operación no está activo");
        }

        if (requestingUser.getRole() != SystemRole.TELLER_EMPLOYEE &&
            requestingUser.getRole() != SystemRole.COMMERCIAL_EMPLOYEE) {
            throw new BusinessException("No tiene permisos para abrir cuentas");
        }
    }

    private void validateAccount(BankAccount account) {
        if (account == null) {
            throw new BusinessException("La cuenta es obligatoria");
        }

        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
            throw new BusinessException("El número de cuenta es obligatorio");
        }

        if (account.getUserAccountID() == null || account.getUserAccountID().trim().isEmpty()) {
            throw new BusinessException("El ID del titular es obligatorio");
        }

        if (account.getAccountType() == null) {
            throw new BusinessException("El tipo de cuenta es obligatorio");
        }

        if (account.getAccountStatus() == null) {
            throw new BusinessException("El estado de la cuenta es obligatorio");
        }

        if (account.getOpeningLocalDate() == null || account.getOpeningLocalDate().toString().trim().isEmpty()) {
            throw new BusinessException("La fecha de apertura es obligatoria");
        }

        if (account.getCurrentBalance() < 0) {
            throw new BusinessException("El saldo inicial no puede ser negativo");
        }
    }

    private void validateUniqueAccountNumber(String accountNumber) {
        if (accountPort.existsByAccountNumber(accountNumber)) {
            throw new BusinessException("Ya existe una cuenta con ese número");
        }
    }

    private User validateAndGetHolder(String UserAccountID) {
        User userAccountID = userPort.findByIdentification(UserAccountID);

        if (userAccountID == null) {
            throw new BusinessException("El titular no existe");
        }

        if (userAccountID.getStatus() == UserStatus.INACTIVE || userAccountID.getStatus() == UserStatus.BLOCKED) {
            throw new BusinessException("No se puede abrir una cuenta a un usuario inactivo o bloqueado");
        }

        return userAccountID;
    }

    private void validateAccountType(BankAccount account) {

        //boolean validType = productCatalogPort.isValidAccountType(account.getAccountType().name());

        /*if (!validType) {
            throw new BusinessException("El tipo de cuenta no es válido según el catálogo bancario");
        }*/
    }

    private void registerAuditLog(BankAccount account, User requestingUser, User holder) {
        String details =
                "{ " +
                "\"accountNumber\": \"" + account.getAccountNumber() + "\", " +
                "\"accountType\": \"" + account.getAccountType().name() + "\", " +
                "\"UserAccountID\": \"" + account.getUserAccountID() + "\", " +
                "\"holderName\": \"" + holder.getFullName() + "\", " +
                "\"initialBalance\": " + account.getCurrentBalance() +
                " }";

    }
    }


