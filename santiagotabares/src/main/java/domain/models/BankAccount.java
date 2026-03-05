package domain.models;

import java.time.LocalDate;

import domain.models.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount extends BankProduct{

    private String accountNumber;
    private String accountType;
    private double currentBalance;
    private AccountStatus status;
    private LocalDate openingDate;

    public BankAccount(String productId, LocalDate creationDate,String accountNumber,String accountType,double currentBalance){
        super(productId, creationDate);
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
    }



}
