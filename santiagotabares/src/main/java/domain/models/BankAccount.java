package domain.models;

import java.time.LocalDate;


import domain.models.enums.*;


public class BankAccount extends BankProduct{

    private String accountNumber;
    private AccountType accountType;
    private double currentBalance;
    private AccountStatus status;
    private LocalDate openingDate;
    private String UserAccountID; 

    public BankAccount(String productId, LocalDate creationDate,String accountNumber,AccountType accountType,double currentBalance){
        super(productId, creationDate);
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currentBalance = currentBalance;

        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("El numero de cuenta es obligatorio.");
        }
        if (currentBalance < 0) {
            throw new IllegalArgumentException("El saldo actual no puede ser negativo.");
        }
        if (UserAccountID == null || UserAccountID.trim().isEmpty()) {
            throw new IllegalArgumentException("El titular de la cuenta es obligatorio.");
        }
        if (openingDate == null || openingDate.toString().isEmpty()) {
            throw new IllegalArgumentException("La fecha de apertura de la cuenta es obligatoria.");
        }
    }

    //getter and setter
    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getUserAccountID() {
        return UserAccountID;
    }

    public double getCurrentBalance(){
        return currentBalance;
    }

    public AccountStatus getAccountStatus(){
        return status;
    }

    public LocalDate getOpeningLocalDate(){
        return openingDate;
    }

    public void setAccountNumber(String NewAccountNumber){
        if(NewAccountNumber == null || NewAccountNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Numero de cuenta obligatorio.");

        }
        this.accountNumber = NewAccountNumber;
    }

    public void setAccountType(AccountType NewaccountType){
        this.accountType = accountType;
    }

    public void setUserAccountID(String NewUserAccountID){
        if (NewUserAccountID == null || NewUserAccountID.trim().isEmpty()) {
            throw new IllegalArgumentException("El Usuario titular debe ser obligatorio.");
        }
        this.UserAccountID = NewUserAccountID;
    }

    public void setAccountStatus(AccountStatus NewStatus){
        this.status = NewStatus;
    }

    public void setOpeningDate(String NewOpeningDate){
        if (NewOpeningDate == null || NewOpeningDate.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de apertura es obligatoria.");
        }
        this.openingDate = LocalDate.parse(NewOpeningDate);
    }

    
    //Metodos de negocio

    public void deposit(double amount){
        if(amount <= 0){
            throw new IllegalArgumentException("El monto a ingresar es invalido.");
        }
        if(this.status != status.ACTIVE){
            throw new IllegalStateException("No se puede depositar el monto. Cuenta inactiva");
        
        }
        this.currentBalance += amount;
    }

    public void withdraw(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a retirar es diferente al minimo permitido.");
        }
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("No se puede retirar el monto. Cuenta inactiva");
        }
        if (this.currentBalance < amount) {
            throw new IllegalStateException("Fondos insuficientes - Saldo actual: " + this.currentBalance);
        }
        this.currentBalance -= amount;
    }


}
